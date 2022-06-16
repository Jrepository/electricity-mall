package com.indi.electricity.mall.model.config.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.indi.electricity.mall.enums.IPairs;

public enum StateEnum implements IPairs<Integer, String, StateEnum> {

    DISABLED(0, "禁用"),
    ENABLE(1, "启用");

    @EnumValue
    Integer key;
    @JsonValue
    String value;

    StateEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override

    public StateEnum get() {
        return this;
    }

    @Override
    public Integer key() {
        return this.key;
    }

    @Override
    public String value() {
        return this.value;
    }
}
