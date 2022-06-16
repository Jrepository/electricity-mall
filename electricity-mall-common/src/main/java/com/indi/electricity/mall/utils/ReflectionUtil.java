package com.indi.electricity.mall.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Log4j2
public class ReflectionUtil {

    public static List<Field> getAllField(Object obj) {
        return getAllField(obj.getClass());
    }

    public static List<Field> getAllField(Class<?> clazz) {
        final List<Field> fields = new LinkedList<>();
        ReflectionUtils.doWithFields(clazz, fields::add);
        return fields;
    }


    public static Field getField(Object obj, String fieldName) {
        return getField(obj.getClass(), fieldName);
    }

    public static Field getField(Class<?> clazz, String fieldName) {
        Field field = ReflectionUtils.findField(clazz, fieldName);
//        ReflectionUtils.makeAccessible(field);
        return field;
    }


    public static Object getFieldValue(Object obj, Field field) {
        try {
            ReflectionUtils.makeAccessible(field);
//             field.setAccessible(Boolean.TRUE);
            return field.get(obj);
        } catch (IllegalAccessException e) {
            log.error("get value error for field({})", field.getName());
        }
        return null;
    }

    public static Object getFieldValue(Object obj, String fieldName) {
        Field field = getField(obj, fieldName);
        return getFieldValue(obj, field);
    }


    public static void setFieldValue(Object obj, Field field, Object value) {
        field.setAccessible(Boolean.TRUE);
        ReflectionUtils.setField(field, obj, value);
//        try {
//            field.set(obj, value);
//        } catch (IllegalAccessException e) {
//            log.error("set value error for field({})", field.getName());
//        }
    }

    public static void setFieldValue(Object obj, String fieldName, Object value) {
        Field field = getField(obj, fieldName);
        setFieldValue(obj, field, value);
    }

    private void setFieldValue(Object obj, String value) {
        final List<Field> fieldList = getAllField(obj);
        fieldList.forEach(field -> {
            ReflectionUtils.makeAccessible(field);
            if (ObjectUtils.isNotEmpty(getFieldValue(obj, field))) {
                setFieldValue(obj, field, value);
            }
        });
    }

}

