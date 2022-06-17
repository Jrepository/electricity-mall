package com.indi.electricity.mall.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.indi.electricity.mall.annotation.TranslateFieldConst.*;

/**
 * jdk8中新增的注解，使用该元注解的注解是可重复的
 * 使用该元注解的注解，可以在同一个地方使用
 * 没有使用该元注解的注解，同一个地方使用会报错
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(TranslateFields.class)
public @interface TranslateField {

    /**
     * 查询方法所在的类
     * enums(根据枚举转译)
     * entity(查询数据库)
     * 指定查询方法所在的类
     *
     * @return
     */
    Class<?> searchClass() default Void.class;

    /**
     * 根据制定方法返回的数据翻译
     *
     * @return
     */
    String searchMethod() default "";

    String searchKey() default TRANSLATE_FIELD_SEARCH_KEY;

    String sourceField() default "";

    /**
     * 不能为空
     *
     * @return
     */
    String targetField() default "";

    String ds() default TRANSLATE_FIELD_DS_DEFAULT;

}
