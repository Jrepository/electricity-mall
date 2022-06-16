package com.indi.electricity.mall.enums;


/**
 * 枚举类型基类
 */
public interface IPairs<K, V, E extends Enum> {

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
     * 返回枚举项的 value
     *
     * @return
     */
    V value();
}
