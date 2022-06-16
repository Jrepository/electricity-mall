package com.indi.electricity.mall.config.processor;

import lombok.Data;

@Data
public class RstConf<T> {

    T data;

    public RstConf(T data) {
        this.data = data;
    }

//    public T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }
}
