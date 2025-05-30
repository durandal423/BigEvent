package com.itheima.anno;

import com.itheima.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented // 元注解 包含在生成的 Javadoc 文档中
@Target({FIELD}) // 元注解 对属性有效
@Retention(RUNTIME) // 元注解 运行时
@Constraint(validatedBy = {StateValidation.class}) // 指定提供校验规则的类
public @interface State {
    // 提供校验失败后的信息提示
    String message() default "state参数的值只能是 已发布 | 草稿 ";

    // 指定分组
    Class<?>[] groups() default {};

    // 负载   获取到State注解的附加信息
    Class<? extends Payload>[] payload() default {};
}
