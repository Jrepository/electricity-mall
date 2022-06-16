package com.indi.electricity.mall.mapper;

import com.indi.electricity.mall.provider.SqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

//@Repository
@Mapper
public interface ReturnTranslateMapper {

    @SelectProvider(type = SqlProvider.class, method = "selectSqlProvider")
    List<Map> queryDatabase(String tableName, Collection<?> fields, String conditionField, List<Object> conditions);

}
