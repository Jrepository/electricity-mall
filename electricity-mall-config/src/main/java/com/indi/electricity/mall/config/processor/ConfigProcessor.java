package com.indi.electricity.mall.config.processor;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.base.Preconditions;
import com.indi.electricity.mall.config.processor.annotation.Config;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class ConfigProcessor implements BeanPostProcessor {

    private final Logger logger = LoggerFactory.getLogger(ConfigProcessor.class);

    @Autowired
    private ConfigBuilder configBuilder;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        for (Field field : findAllField(clazz)) {
            processField(bean, field);
        }
        return bean;
    }

    private List<Field> findAllField(Class<?> clazz) {
        final List<Field> res = new LinkedList<>();
        ReflectionUtils.doWithFields(clazz, res::add);
        return res;
    }

    private void processField(Object bean, Field field) {
        Config annotation = AnnotationUtils.getAnnotation(field, Config.class);
        if (annotation == null) {
            return;
        }
        String name = annotation.name();
        boolean findChild = annotation.findChild();
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException(String.format("[@Config] empty name for field %s.", field));
        }
        JavaType javaType = getValidJavaType(field);
        if (findChild && !javaType.isTypeOrSubTypeOf(Map.class)) {
            throw new IllegalArgumentException(String.format("[@Config] must be map for field %s.", field));
        }
        RstConf<?> config = configBuilder.build(name, annotation.defaultValue(), findChild, javaType, field);
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, bean, config);
    }

    private JavaType getValidJavaType(Field field) {
        Type genericType = field.getGenericType();
        JavaType javaType = TypeFactory.defaultInstance().constructType(genericType);

        // 类型必须为RstConf<xxx>
        Preconditions.checkArgument(javaType.isTypeOrSubTypeOf(RstConf.class),
                "[@Config] Invalid type for field %s, must be RstConf.", field);
        Preconditions.checkArgument(javaType.containedTypeCount() == 1,
                "[@Config] Invalid type for filed %s, must be RstConf<xxx>.", field);

        JavaType subJavaType = javaType.containedType(0);
        if (subJavaType.isTypeOrSubTypeOf(List.class) || subJavaType.isTypeOrSubTypeOf(Set.class)) {
            checkSubTypes(subJavaType, 1, field);
        } else if (subJavaType.isTypeOrSubTypeOf(Map.class)) {
            checkSubTypes(subJavaType, 2, field);
        }
        return subJavaType;
    }

    private void checkSubTypes(JavaType type, int typeCount, Field field) {
        Preconditions
                .checkArgument(type.containedTypeCount() == typeCount, "[Config] Invalid type for field %s.", field);
    }
}
