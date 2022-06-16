package com.indi.electricity.mall.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.indi.electricity.mall.vo.KeyValueVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


/**
 * 根据枚举生成生成KeyValue对象列表
 */
@Component
public class KeyValueUtil {

    private static Logger logger = LoggerFactory.getLogger(KeyValueUtil.class);

    private static final String ERROR_INFO = "KeyValueUtil.enumToKeyValue error:{}";

    private static final String ENUM_CLASSPATH = "java.lang.Enum";

    private static final String KEY_METHOD_NAME = "key";

    private static final String VALUE_METHOD_NAME = "value";

    private static final String VALUE1_METHOD_NAME = "value1";

    /**
     * 根据key查询枚举类型的key-value列表
     *
     * @param typeList
     * @param enumClass
     * @return
     */
    public static List<KeyValueVo> enumToKeyValue(List typeList, Class<?> enumClass) {
        List<KeyValueVo> keyValueVoList = new ArrayList<>();

        // 参数为空 或 enumClass不是枚举类
        if (CollectionUtils.isEmpty(typeList)
                || !ENUM_CLASSPATH.equals(enumClass.getSuperclass().getCanonicalName())) {
            return keyValueVoList;
        }

        Method[] methods = enumClass.getMethods();

        // 获取key方法
        Method keyMethod;
        try {
            keyMethod = enumClass.getMethod(KEY_METHOD_NAME);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            logger.info(ERROR_INFO + " has no method key(). {}", enumClass, e);
            return keyValueVoList;
        }

        //获取value方法，如果有value()方法，使用value(),否则使用value1()
        Method valueMethod = Arrays.stream(methods)
                .filter(method -> VALUE_METHOD_NAME.equals(method.getName()) || VALUE1_METHOD_NAME.equals(method.getName()))
                .findAny().get();

        // Step3: 根据传入的key列表进行筛选
        Enum[] enums = (Enum[]) enumClass.getEnumConstants();
        for (Enum oneEnum : enums) {
            try {
                Object key = keyMethod.invoke(oneEnum);
                Object value = valueMethod.invoke(oneEnum);
                if (typeList.contains(key)) {
                    KeyValueVo keyValueVo = new KeyValueVo(key, value);
                    keyValueVoList.add(keyValueVo);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                logger.info(ERROR_INFO + " invoke method error. {}", enumClass, e);
            }
        }
        return keyValueVoList;
    }
}
