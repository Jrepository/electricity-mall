package com.indi.electricity.mall.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * 错误：Unsupported conversion from LONG to java.sql.Timestamp
 * 解决：@Component
 *
 * 定义类型：注释后仍然可以正常显示
 * @MappedJdbcTypes({JdbcType.BIGINT})
 * @MappedTypes({Date.class})
 *
 * @author: admin
 */

@Component
//@MappedJdbcTypes({JdbcType.BIGINT})
//@MappedTypes({Date.class})
public class DateTypeHandler extends BaseTypeHandler<Date> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Date date, JdbcType jdbcType) throws SQLException {
        preparedStatement.setLong(i, date.getTime());
    }

    @Override
    public Date getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Long tmpToDate = resultSet.getLong(s);
        return tmpToDate.compareTo(0L) > 0 ? new Date(tmpToDate) : null;

    }

    @Override
    public Date getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Long tmpToDate = resultSet.getLong(i);
        return tmpToDate.compareTo(0L) > 0 ? new Date(tmpToDate) : null;
    }

    @Override
    public Date getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        Long tmpToDate = callableStatement.getLong(i);
        return tmpToDate.compareTo(0L) > 0 ? new Date(tmpToDate) : null;
    }

}
