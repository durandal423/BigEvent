package com.itheima.validation;

import com.itheima.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class StateValidation implements ConstraintValidator<State, String> {
    private static final Set<String> ALLOWED_VALUES = Set.of("已发布", "草稿");
    /**
     * @param value   将来要校验的数据
     * @param context context in which the constraint is evaluated
     * @return false:校验不通过 true:校验通过
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 提供校验规则
        return ALLOWED_VALUES.contains(value);
    }
}
