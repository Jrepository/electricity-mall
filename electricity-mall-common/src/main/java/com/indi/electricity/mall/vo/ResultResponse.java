package com.indi.electricity.mall.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.indi.electricity.mall.exception.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultResponse<T> {

    private String code;

    private String message;


    private Long total;

    private T result;


    public static ResultResponse success() {
        return success(null);
    }

    public ResultResponse(String code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public static <T> ResultResponse success(T data) {
        return new ResultResponse(ExceptionEnum.SUCCESS.key(), ExceptionEnum.SUCCESS.key(), data);
    }

    public static <T> ResultResponse<List<T>> successPage(IPage<T> page) {
        return new ResultResponse(ExceptionEnum.SUCCESS.key(), ExceptionEnum.SUCCESS.key(), page.getTotal(), page.getRecords());
    }

    public static ResultResponse error(ExceptionEnum exceptionEnum) {
        return error(exceptionEnum.key(), exceptionEnum.value());
    }

    public static ResultResponse error(String code, String message) {
        return new ResultResponse(code, message, null);
    }


}
