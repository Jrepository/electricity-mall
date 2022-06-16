package com.indi.electricity.mall.utils;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DuplicateElementUtil {

    public static <E> List<E> getDuplicateElements(List<E> list) {
        return list.stream()                              // list 对应的 Stream
                .collect(Collectors.toMap(e -> e, e -> 1, Integer::sum)) // 获得元素出现频率的 Map，键为元素，值为元素出现的次数
                .entrySet()
                .stream()                              // 所有 entry 对应的 Stream
                .filter(e -> e.getValue() > 1)         // 过滤出元素出现次数大于 1 (重复元素）的 entry
                .map(Map.Entry::getKey)                // 获得 entry 的键（重复元素）对应的 Stream
                .collect(Collectors.toList());         // 转化为 List
    }

    public static <E> List<E> getDuplicateElementsGuava(List<E> list) {
        List<E> result = new ArrayList<>();
        for (Multiset.Entry<E> entry : HashMultiset.create(list).entrySet()) {
            if (entry.getCount() > 1) {
                result.add(entry.getElement());
            }
        }
        return result;
    }

    public static <E> List<E> getDuplicateElementsGuavaWithLambda(List<E> list) {
        return HashMultiset.create(list).entrySet().stream()
                .filter(w -> w.getCount() > 1)
                .map(Multiset.Entry::getElement)
                .collect(Collectors.toList());
    }

    public static <E> List<E> getDuplicateElements1(List<E> list) {
        List<E> result = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (E e : list) {
            // 如果不存在返回 -1。
            if (builder.indexOf("," + e + ",") > -1) {
                result.add(e);
            } else {
                builder.append(",").append(e).append(",");
            }
        }
        return result;
    }
}
