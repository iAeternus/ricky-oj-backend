package org.ricky.common.validation.id.acceptedproblem;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

import static org.ricky.common.constants.CommonConstants.ACCEPTED_PROBLEM_ID_PREDIX;
import static org.ricky.common.utils.ValidationUtils.isBlank;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/28
 * @className AcceptedProblemIdValidator
 * @desc
 */
@Deprecated
public class AcceptedProblemIdValidator implements ConstraintValidator<AcceptedProblemId, String> {

    private static final Pattern PATTERN = Pattern.compile("^" + ACCEPTED_PROBLEM_ID_PREDIX + "[0-9]{17,19}$");

    @Override
    public boolean isValid(String acceptedProblemId, ConstraintValidatorContext constraintValidatorContext) {
        if (isBlank(acceptedProblemId)) {
            return true;
        }
        return isAcceptedProblemId(acceptedProblemId);
    }

    public static boolean isAcceptedProblemId(String acceptedProblemId) {
        return PATTERN.matcher(acceptedProblemId).matches();
    }

}
