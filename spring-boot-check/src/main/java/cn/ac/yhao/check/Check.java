package cn.ac.yhao.check;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数校验注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Check {

    // 字段校验规则，格式：字段名+校验规则+冒号+错误信息，例如：id<10:ID必须少于10
    String[] value();
}
