package com.indi.electricity.mall.enums;

/**
 * 枚举类型基类
 */
public interface ITriple<K, V1, V2, E extends Enum> {
    /**
     * 返回枚举对象
     *
     * @return
     */
    E get();

    /**
     * 返回枚举项的 key
     *
     * @return
     */
    K key();

    /**
     * 返回枚举项的 value1
     *
     * @return
     */
    V1 value1();

    /**
     * 返回枚举项的 value2
     *
     * @return
     */
    V2 value2();
}
