package com.indi.electricity.mall.utils;

import com.google.common.base.CaseFormat;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: admin
 */
public class GauvaUtil {

    /**
     * 驼峰转下划线
     *
     * @param str
     * @return
     */
    public static String toLowerUnderscore(String str) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, str);
    }

    /**
     * 下划线转驼峰
     *
     * @param str
     * @return
     */
    public static String toLowerCamel(String str) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, str);
    }


    public static void toLowerCamel(List<Map> dataList,List<Map> targetList) {
        for (Map map : dataList) {
            Map target = (Map) map.keySet().stream()
                    .collect(Collectors.toMap(key -> toLowerCamel(key.toString()), key -> map.get(key)));
            targetList.add(target);
        }

    }

    public static List<String> toLowerCamel(Collection<?> list) {
        return list.stream().map(oneData -> toLowerCamel((String) oneData)).collect(Collectors.toList());
    }
}
