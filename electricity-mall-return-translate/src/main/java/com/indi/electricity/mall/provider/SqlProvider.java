package com.indi.electricity.mall.provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: admin
 */
public class SqlProvider {

    public String selectSqlProvider(String tableName, Collection<?> fields, String conditionField, List<Object> conditions) {
        SQL sql = new SQL() {{
            SELECT(StringUtils.join( fields,","));
            FROM(tableName);
            WHERE(String.format("%s in (%s)", conditionField, getCondition(conditions)));
        }};
        return sql.toString();
    }

    private String getCondition(List<Object> list) {
        // Number
        if (list.get(0) instanceof Number) {
            return StringUtils.join(list, ",");
        }

        List<String> valueList = list.stream()
                .map(item -> "'" + item + "'")
                .collect(Collectors.toList());

        return StringUtils.join(valueList, ",");

    }
}
