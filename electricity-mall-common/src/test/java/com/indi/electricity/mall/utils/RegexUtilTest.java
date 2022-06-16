package com.indi.electricity.mall.utils;

import org.junit.jupiter.api.Test;


public class RegexUtilTest extends BaseTest {

    @Test
    void isTrue() {
    }


    @Test
    void split() {
//        String[] split = RegexUtil.split("a","\\*");
        String[] split = RegexUtil.split("a", ",\\n|,|，｜，\\n");
        output(split.length);
        output(split[0]);
    }

}
