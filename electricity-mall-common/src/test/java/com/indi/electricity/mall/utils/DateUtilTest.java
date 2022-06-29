package com.indi.electricity.mall.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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

    @Test
    public void test() {
        System.out.println( new Date() instanceof Date);
        System.out.println(LocalDate.now() instanceof LocalDate);
        System.out.println(LocalDateTime.now() instanceof LocalDateTime);
    }
}
