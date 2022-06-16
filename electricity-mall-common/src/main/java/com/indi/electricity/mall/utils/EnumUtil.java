package com.indi.electricity.mall.utils;

import com.indi.electricity.mall.enums.IPairs;

public class EnumUtil {

    public static <E extends Enum<?> & IPairs> E codeOf(Class<E> enumClass, Object key) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.key().equals(key))
                return e;
        }
        return null;
    }
}
