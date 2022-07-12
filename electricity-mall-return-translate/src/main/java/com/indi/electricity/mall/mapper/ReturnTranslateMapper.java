package com.indi.electricity.mall.mapper;

import com.indi.electricity.mall.provider.SqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 该类中使用的注解是@Mapper，使用@Repository报错
 *
 * @author: admin
 */

@Mapper
public interface ReturnTranslateMapper {

    /**
     * 查询数据库
     *
     * @param tableName
     * @param fields
     * @param conditionField
     * @param conditions
     * @return: java.util.List<java.util.Map>
     **/
    @SelectProvider(type = SqlProvider.class, method = "selectSqlProvider")
    List<Map> queryDatabase(String tableName, Collection<?> fields, String conditionField, List<Object> conditions);

}
