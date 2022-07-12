package com.indi.electricity.mall.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: admin
 */
public class JsonUtil {

    private static Gson gson = new Gson();

    private JsonUtil() {
    }

    public static String encode(Object src) {
        return gson.toJson(src);
    }

    public static <T> T decode(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static <T> T decode(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    public static <T> List<T> decode(Class<T> searchClass, List<Map> result) {
        return result.stream()
                .map(oneData -> JsonUtil.decode(JsonUtil.encode(oneData), searchClass))
                .collect(Collectors.toList());
    }

}
