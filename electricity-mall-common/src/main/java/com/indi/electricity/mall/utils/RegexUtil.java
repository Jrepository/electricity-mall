package com.indi.electricity.mall.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @author: admin
 */
public class RegexUtil {

    /**
     * [^\\*^\\+]+(\*\d+)?(\+[^\\*^\\+]+(\*\d+)?)?
     * [^\\*^\\+]+(\*\d+)?(\+[^\\*^\\+]+(\*\d+)?)*
     * [^\\*^\\+]+(\*\d+)?(\+[^\\*^\\+]+(\*\d+)?){0,2}
     * <p>
     * 根据+切割后，得到的内容中可以没有*，若有*，*前的内容不能有*与+，*后的内容只能是数字
     * <p>
     * 正确：a、a+a、a*1+a、a*1+a*1、a+a*1、a*1+a*1
     * 错误：a+、+a、a**1、a++1、a+*1、a*、a*1d、*1
     */
    public static final String REGEX = "[^\\*^\\+]+(\\*\\d+)?(\\+[^\\*^\\+]+(\\*\\d+)?)*";

    public static boolean isTrue(String val) {
        return Pattern.matches(REGEX, val.trim());
    }

    public static boolean isTrue(String regex, String val) {
        return Pattern.matches(regex, val.trim());
    }



    /**
     * regex = "，\\n|,"  切割 ,或者 ,空格
     * regex = "\\*"     切割*
     * regex = "\\+"     切割+
     * @param value
     * @param regex
     * @return
     */
    public static String[] split(String value, String regex) {
        if (StringUtils.isEmpty(value)) {
            return new String[0];
        }
        return value.split(regex);
    }
}
