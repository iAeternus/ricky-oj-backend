package org.ricky.common.validation.id.submit;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

import static org.ricky.common.constants.CommonConstants.SUBMIT_ID_PREFIX;
import static org.ricky.common.utils.ValidationUtils.isBlank;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/28
 * @className SubmitIdValidator
 * @desc
 */
@Deprecated
public class SubmitIdValidator implements ConstraintValidator<SubmitId, String> {

    private static final Pattern PATTERN = Pattern.compile("^" + SUBMIT_ID_PREFIX + "[0-9]{17,19}$");

    @Override
    public boolean isValid(String submitId, ConstraintValidatorContext constraintValidatorContext) {
        if (isBlank(submitId)) {
            return true;
        }
        return isSubmitId(submitId);
    }

    public static boolean isSubmitId(String submitId) {
        return PATTERN.matcher(submitId).matches();
    }

}
