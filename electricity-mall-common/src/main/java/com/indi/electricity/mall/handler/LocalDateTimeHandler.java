package com.indi.electricity.mall.handler;

import com.indi.electricity.mall.utils.DateUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
//@MappedJdbcTypes({JdbcType.BIGINT})
//@MappedTypes({LocalDateTime.class})
public class LocalDateTimeHandler extends BaseTypeHandler<LocalDateTime> {

    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime localDateTime, JdbcType jdbcType) throws SQLException {
        ps.setLong(i, DateUtil.getLong(localDateTime));
    }

    public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return DateUtil.getDateTimeFromTimestamp(rs.getLong(columnName));
    }

    public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return DateUtil.getDateTimeFromTimestamp(rs.getLong(columnIndex));
    }

    public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return DateUtil.getDateTimeFromTimestamp(cs.getLong(columnIndex));
    }
}
