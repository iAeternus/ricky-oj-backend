package org.ricky.common.validation.number;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static org.ricky.common.utils.ValidationUtils.isNull;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/29
 * @className EnumKeyValidator
 * @desc
 */
public class EnumKeyValidator implements ConstraintValidator<EnumKey, Short> {

    private short min;
    private short max;

    @Override
    public void initialize(EnumKey enumKey) {
        this.min = enumKey.min();
        this.max = enumKey.max();
    }

    @Override
    public boolean isValid(Short key, ConstraintValidatorContext constraintValidatorContext) {
        if (isNull(key)) {
            return true;
        }
        return key >= min && key <= max;
    }

}
