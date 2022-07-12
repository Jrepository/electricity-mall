package com.indi.electricity.mall.handler;

import java.util.List;

/**
 * @author: admin
 */
public interface IQueryHandler {

    /**
     * 获取指定方法的返回值
     *
     * @param searchClass     方法所在类
     * @param searchMethod
     * @param searchValueList
     * @return
     */
    List queryData(Class<?> searchClass, String searchMethod, List<Object> searchValueList);

    /**
     * 根据指定类获取返回值
     *
     * @param searchClass     枚举类，实体类
     * @param searchKey
     * @param sourceFieldList
     * @param searchValueList
     * @return
     */
    <T> List<T> queryData(Class<T> searchClass, String searchKey, List<String> sourceFieldList, List<Object> searchValueList);
}
