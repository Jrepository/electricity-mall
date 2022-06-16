package com.indi.electricity.mall.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

/**
 * 测试方法与项目无关，只使用@Test注解即可
 */
public class DateUtilTest {

    @Test
    public void getStartTimeTest() {
        System.out.println(DateUtil.getStartTime(LocalDate.now()));
    }

    @Test
    public void getEndTimeTest() {
        System.out.println(DateUtil.getEndTime(LocalDate.now()));
    }

    @Test
    public void getDateTimeFromTimestampTest() {
        System.out.println(DateUtil.getDateTimeFromTimestamp(1652764004052l));
    }
}
