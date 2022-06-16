package com.indi.electricity.mall.config.processor.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.indi.electricity.mall.config.processor.ConfigAnnotationConst.CONFIG_ANNOTATION_DEFAULT_VALUE;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Config {

    /**
     * 配置英文名称
     *
     * @return
     */
    String name();

    /**
     * 默认值
     *
     * @return
     */
    String defaultValue() default CONFIG_ANNOTATION_DEFAULT_VALUE;

    /**
     * 是否取子节点数据，为true时,必须是map,只有一级子类
     *
     * @return
     */
    boolean findChild() default false;

}
