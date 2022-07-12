package com.indi.electricity.mall.utils;

import org.springframework.core.env.Environment;

import java.util.Optional;

/**
 * @author: admin
 */
public class SystemUtil {

    private static final Environment environment;
    private static final String property;

    static {
        environment = (Environment) SpringUtil.getBean(Environment.class);
        property = getProperties("spring.profiles.active");
    }

    public static boolean isProd() {
        return "prod".equalsIgnoreCase(((Environment) SpringUtil.getBean(Environment.class)).getProperty("spring.profiles.active"));
    }

    public static boolean isTest() {
        return "test".equalsIgnoreCase(property);
    }

    public static boolean isDev() {
        return "dev".equalsIgnoreCase(property);
    }

    public static String getProperties(String key, String defaultVal) {
        return (String) Optional.ofNullable(environment.getProperty(key)).orElse(defaultVal);
    }

    public static String getProperties(String key) {
        return environment.getProperty(key);
    }

    public static String getProperties() {
        return property;
    }
}
