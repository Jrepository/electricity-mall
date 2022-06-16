package com.indi.electricity.mall.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation that be grouping mapping definitions for property.
 *
 * <p>
 * <b>How to use:</b>
 * The annotation that be grouping mapping definitions for property.
 * How to use:
 * public interface UserMapper {
 *
 * @TranslateField({
 * @TranslateField(property = "id", column = "id", id = true),
 * @TranslateField(property = "name", column = "name"),
 * @TranslateField(property = "email" column = "id", one = @One(select = "selectUserEmailById", fetchType = FetchType.LAZY)),
 * @TranslateField(property = "telephoneNumbers" column = "id", many = @Many(select = "selectAllUserTelephoneNumberById", fetchType = FetchType.LAZY))
 * })
 * @Select("SELECT id, name FROM users WHERE id = #{id}")
 * User selectById(int id);
 * }
 * <p>
 * Author:
 * Clinton Begin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TranslateFields {

    TranslateField[] value() default {};
}
