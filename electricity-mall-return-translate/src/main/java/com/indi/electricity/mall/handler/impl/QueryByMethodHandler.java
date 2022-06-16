package com.indi.electricity.mall.handler.impl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.indi.electricity.mall.handler.IQueryHandler;
import com.indi.electricity.mall.mapper.ReturnTranslateMapper;
import com.indi.electricity.mall.utils.GauvaUtil;
import com.indi.electricity.mall.utils.JsonUtil;
import com.indi.electricity.mall.utils.ReflectionUtil;
import com.indi.electricity.mall.vo.KeyValueVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class QueryByMethodHandler implements IQueryHandler {

    private static final String SERIALVERSION_UID = "serialVersionUID";

    @Autowired
    ReturnTranslateMapper returnTranslateMapper;

    @Override
    public List<?> queryData(Class<?> searchClass, String searchMethod, List<Object> searchValueList) {
        try {
            Method method = searchClass.getDeclaredMethod(searchMethod, List.class);
            Object result = method.invoke(searchClass.newInstance(), searchValueList);
            return (List) result;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> List<T> queryData(Class<T> searchClass, String searchKey, List<String> sourceFieldList, List<Object> searchValueList) {
        List result;
        if (searchClass.isEnum()) {
            result = getKeyValueVoList(searchClass);
        } else {
            // 根据searchClass查询数据库
            result = queryDb(searchClass, searchKey, sourceFieldList, searchValueList);
        }
        return result;
    }


    public static List<KeyValueVo> getKeyValueVoList(Class<?> clazz) {
        List<KeyValueVo> list = new ArrayList<>();
        try {
            Object[] objects = clazz.getEnumConstants();
            Method keyMethod = clazz.getMethod("key");
            Method valueMethod = clazz.getMethod("value");
            for (Object object : objects) {
                Object key = keyMethod.invoke(object);
                Object value = valueMethod.invoke(object);
                list.add(new KeyValueVo(key, value));
            }
        } catch (Exception e) {
//            logger
        } finally {
            return list;
        }
    }


    private <T> List<T> queryDb(Class<T> searchClass, String searchKey, List<String> sourceFieldList, List<Object> searchValueList) {
        TableName tableNameAnnotation = searchClass.getAnnotation(TableName.class);
        String tableName;
        if (tableNameAnnotation == null) {
            tableName = GauvaUtil.toLowerUnderscore(searchClass.getSimpleName());
        } else {
            tableName = tableNameAnnotation.value();
        }

        List<String> fields = new LinkedList<>();

        if (!CollectionUtils.isEmpty(sourceFieldList)) {
            sourceFieldList = sourceFieldList.stream().map(item -> GauvaUtil.toLowerUnderscore(item)).collect(Collectors.toList());
        }

        searchKey = GauvaUtil.toLowerUnderscore(searchKey);
        for (Field field : ReflectionUtil.getAllField(searchClass)) {
            TableField tableFieldAnnotation = field.getAnnotation(TableField.class);
            String tableFieldName = null;
            if (tableFieldAnnotation == null) {
                tableFieldName = SERIALVERSION_UID.equals(field.getName()) ? null : GauvaUtil.toLowerUnderscore(field.getName());
            } else if (tableFieldAnnotation.exist()) {
                String fieldName = tableFieldAnnotation.value();
                tableFieldName = StringUtils.isEmpty(fieldName) ? GauvaUtil.toLowerUnderscore(field.getName()) : fieldName;
            }
            if (StringUtils.isNotBlank(tableFieldName)
                    && (searchKey.contains(tableFieldName) || CollectionUtils.isEmpty(sourceFieldList) || sourceFieldList.contains(tableFieldName))) {
                fields.add(tableFieldName);
            }
        }

        List<Map> dataList = returnTranslateMapper.queryDatabase(tableName, fields, searchKey, searchValueList);
        List<Map> result = new ArrayList<>();
        GauvaUtil.toLowerCamel(dataList, result);
        return JsonUtil.decode(searchClass, result);
    }


}
