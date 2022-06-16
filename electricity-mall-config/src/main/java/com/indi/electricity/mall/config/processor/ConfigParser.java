package com.indi.electricity.mall.config.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.indi.electricity.mall.config.processor.ConfigAnnotationConst.CONFIG_ANNOTATION_DEFAULT_VALUE;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;

@Component
public class ConfigParser {

    private static final Logger logger = LoggerFactory.getLogger(ConfigParser.class);

    public static final ObjectMapper MAPPER = new ObjectMapper();

    public Object parse(JavaType type, String value, String defaultValue, Field field) {
        if (StringUtils.isBlank(value)) {
            return defaultValue(type, defaultValue, field);
        }
        return parseOf(type, value);
    }

    public Object parseOf(JavaType type, String valueStr) {
        if (type.isTypeOrSubTypeOf(List.class)) {
            return parserOfList(type, valueStr);
        } else if (type.isTypeOrSubTypeOf(Set.class)) {
            return parserOfSet(type, valueStr);
        } else if (type.isTypeOrSubTypeOf(Map.class)) {
            return parserOfMap(type, valueStr);
        } else if (type.isTypeOrSubTypeOf(Integer.class)) {
            return parseInteger(valueStr);
        } else if (type.isTypeOrSubTypeOf(Long.class)) {
            return parseLong(valueStr);
        } else if (type.isTypeOrSubTypeOf(Boolean.class)) {
            return parseBoolean(valueStr);
        } else if (type.isTypeOrSubTypeOf(Double.class)) {
            return parseDouble(valueStr);
        } else if (type.isTypeOrSubTypeOf(String.class)) {
            return parseString(valueStr);
        } else {
            return parseJson(type, valueStr);
        }
    }

    private List<?> parserOfList(JavaType type, String valueStr) {
        JavaType itemType = type.containedType(0);
        List result = new ArrayList();
        try {
            List valueList = ConfigParser.MAPPER.readValue(valueStr, type);
            valueList.stream().forEach(value -> result.add(parseOf(itemType, value.toString())));
        } catch (JsonProcessingException e) {
            logger.error("");
            e.printStackTrace();
        }
        return result;
    }

    private Set<?> parserOfSet(JavaType type, String valueStr) {
        JavaType itemType = type.containedType(0);
        Set result = new HashSet();
        try {
            Set valueSet = ConfigParser.MAPPER.readValue(valueStr, type);
            valueSet.stream().forEach(value -> result.add(parseOf(itemType, value.toString())));
        } catch (JsonProcessingException e) {
            logger.error("");
            e.printStackTrace();
        }
        return result;
    }

    private Map<?, ?> parserOfMap(JavaType type, String valueStr) {
        JavaType keyType = type.containedType(0);
        JavaType valueType = type.containedType(1);
        Map result = new HashMap();
        try {
            Map valueMap = ConfigParser.MAPPER.readValue(valueStr, type);
            valueMap.keySet().stream().forEach(key ->
                    result.put(parseOf(keyType, key.toString()), parseOf(valueType, valueMap.get(key).toString()))
            );
        } catch (JsonProcessingException e) {
            logger.error("");
            e.printStackTrace();
        }
        return result;
    }

    private Integer parseInteger(String valueStr) {
        return Integer.valueOf(valueStr);
    }

    private Long parseLong(String valueStr) {
        return Long.valueOf(valueStr);
    }

    private Boolean parseBoolean(String valueStr) {
        return Boolean.valueOf(valueStr);
    }

    private Double parseDouble(String valueStr) {
        return Double.valueOf(valueStr);
    }

    private String parseString(String valueStr) {
        return valueStr;
    }

    private Object parseJson(JavaType javaType, String valueStr) {
        try {
            return ConfigParser.MAPPER.readValue(valueStr, javaType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Object defaultValue(JavaType javaType, String def, Field field) {
        if (javaType.isContainerType()) {
            if (StringUtils.isNotBlank(def)) {
                logger.warn("[Config] annotation field: {}, [Set,List,Map] not support default, default is [empty]",
                        field);
            }
        }
        if (javaType.isTypeOrSubTypeOf(List.class)) {
            return emptyList();
        } else if (javaType.isTypeOrSubTypeOf(Set.class)) {
            return emptySet();
        } else if (javaType.isTypeOrSubTypeOf(Map.class)) {
            return emptyMap();
        } else if (javaType.isTypeOrSubTypeOf(Integer.class)) {
            return toInt(def, field);
        } else if (javaType.isTypeOrSubTypeOf(Long.class)) {
            return toLong(def, field);
        } else if (javaType.isTypeOrSubTypeOf(Double.class)) {
            return toDouble(def, field);
        } else if (javaType.isTypeOrSubTypeOf(Boolean.class)) {
            return toBoolean(def, field);
        } else if (javaType.isTypeOrSubTypeOf(String.class)) {
            return toStr(def);
        } else {
            return defaultObject(javaType, def);
        }
    }

    private int toInt(String defaultValue, Field field) {
        if (CONFIG_ANNOTATION_DEFAULT_VALUE.equals(defaultValue)) {
            return 0;
        }
        try {
            return Integer.parseInt(defaultValue);
        } catch (final NumberFormatException e) {
            throw illegalType(field, e);
        }
    }

    private long toLong(String defaultValue, Field field) {
        if (CONFIG_ANNOTATION_DEFAULT_VALUE.equals(defaultValue)) {
            return 0L;
        }
        try {
            return Long.parseLong(defaultValue);
        } catch (final NumberFormatException e) {
            throw illegalType(field, e);
        }
    }

    private boolean toBoolean(String defaultValue, Field field) {
        if (CONFIG_ANNOTATION_DEFAULT_VALUE.equals(defaultValue)) {
            return false;
        }
        try {
            return Boolean.parseBoolean(defaultValue);
        } catch (final NumberFormatException e) {
            throw illegalType(field, e);
        }
    }

    private double toDouble(String defaultValue, Field field) {
        if (CONFIG_ANNOTATION_DEFAULT_VALUE.equals(defaultValue)) {
            return 0.0D;
        }
        try {
            return Double.parseDouble(defaultValue);
        } catch (final NumberFormatException e) {
            throw illegalType(field, e);
        }
    }

    private String toStr(String defaultValue) {
        if (CONFIG_ANNOTATION_DEFAULT_VALUE.equals(defaultValue)) {
            return null;
        }
        return defaultValue;
    }

    private <T> T defaultObject(JavaType javaType, String def) {
        if ("null".equals(def)) {
            return null;
        }
        try {
            return ConfigParser.MAPPER.readValue("{}", javaType);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private IllegalArgumentException illegalType(Field field, Throwable e) {
        return new IllegalArgumentException(String.format("[Config] Invalid default type for field %s", field), e);
    }
}
