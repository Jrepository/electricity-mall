package com.indi.electricity.mall.utils;

import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;

import java.util.Optional;

public class SystemUtilTest extends BaseTest {


    @Test
    void isProdTest() {
        output(SystemUtil.isProd());
    }

    @Test
    void isTestTest() {
        output(SystemUtil.isTest());
    }

    @Test
    void isDevTest() {
        output(SystemUtil.isDev());
    }


    @Test
    void getPropertiesTest() {
        output(SystemUtil.getProperties());
    }

}
