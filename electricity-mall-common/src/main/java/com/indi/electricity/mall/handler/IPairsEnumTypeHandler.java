package com.indi.electricity.mall.handler;

import com.indi.electricity.mall.enums.IPairs;
import com.indi.electricity.mall.utils.EnumUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.cglib.core.EmitUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IPairsEnumTypeHandler<E extends Enum<?> & IPairs> extends BaseTypeHandler<IPairs> {

    private Class<E> type;

    public IPairsEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, IPairs parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setObject(i, parameter.key());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return rs.wasNull() ? null : codeOf(code);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return rs.wasNull() ? null : codeOf(code);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return cs.wasNull() ? null : codeOf(code);
    }

    private E codeOf(int key){
        try {
            return EnumUtil.codeOf(type, key);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot convert " + key + " to " + type.getSimpleName() + " by key value.", ex);
        }
    }
}
