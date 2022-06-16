package com.indi.electricity.mall.exception;

import com.indi.electricity.mall.enums.IPairs;

public enum ExceptionEnum implements IPairs<String, String, ExceptionEnum> {
    SUCCESS("200", "成功"),
    BODY_NOT_MATCH("4000", "请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH("4001", "请求的数字签名不匹配!"),
    NOT_FOUND("4004", "未找到该资源!"),
    INTERNAL_SERVER_ERROR("5000", "服务器内部错误!"),
    NUMBER_FORMAT_ERROR("5000", "服务器内部错误!"),
    SERVER_BUSY("5003", "服务器正忙，请稍后再试!");

    String code;
    String msg;

    ExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public ExceptionEnum get() {
        return this;
    }

    @Override
    public String key() {
        return this.code;
    }

    @Override
    public String value() {
        return this.value();
    }
}
