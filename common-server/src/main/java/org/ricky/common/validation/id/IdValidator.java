package org.ricky.common.validation.id;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static org.ricky.common.utils.ValidationUtils.isBlank;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/28
 * @className IdValidator
 * @desc
 */
public class IdValidator implements ConstraintValidator<Id, String> {

    private String prefix;

    @Override
    public void initialize(Id id) {
        this.prefix = id.prefix();
    }

    @Override
    public boolean isValid(String id, ConstraintValidatorContext constraintValidatorContext) {
        if (isBlank(id)) {
            return true;
        }
        return isId(id, prefix);
    }

    public static boolean isId(String id, String prefix) {
        return PrefixFactory.getInstance().matches(id, prefix);
    }

}
