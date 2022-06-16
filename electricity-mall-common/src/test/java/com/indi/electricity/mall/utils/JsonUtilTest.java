package com.indi.electricity.mall.utils;

import com.google.gson.Gson;
import com.indi.electricity.mall.vo.KeyValueVo;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonUtilTest extends BaseTest {

    @Test
    void encodeTest() {

        Map<String, Object> map1 = new HashMap<String, Object>() {{
            put("id", 1);
            put("str", "测试");
        }};
        Map<String, Object> map2 = new HashMap<>();
        map2.put("id", 1);
        map2.put("str", "测试");
        output(JsonUtil.encode(map1));//JsonUtil.encode(map1) 返回值 null
        output(JsonUtil.encode(map2));
        output(JsonUtil.encode(new KeyValueVo(1, 1)));
    }

}
