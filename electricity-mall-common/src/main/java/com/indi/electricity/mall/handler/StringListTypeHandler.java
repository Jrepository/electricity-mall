package com.indi.electricity.mall.handler;

import com.google.common.reflect.TypeToken;
import com.indi.electricity.mall.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StringListTypeHandler implements TypeHandler<List<String>> {

//    public static final TypeReference<List<String>> typeReference = new TypeReference<List<String>>() {};
    public static final Type TYPE = new TypeToken<List<String>>(){}.getType();

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, List<String> list, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, JsonUtil.encode(list));
    }

    @Override
    public List<String> getResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return JsonUtil.decode(json, TYPE);
    }

    @Override
    public List<String> getResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return JsonUtil.decode(json, TYPE);
    }

    @Override
    public List<String> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return JsonUtil.decode(json, TYPE);
    }

}
