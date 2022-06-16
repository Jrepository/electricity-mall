package com.indi.electricity.mall.utils;

import com.indi.electricity.mall.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;

public class SpringUtilTest extends BaseTest {


    @Test
    void getBeanTest() {
        output(SpringUtil.getBean(GlobalExceptionHandler.class));
    }
}
