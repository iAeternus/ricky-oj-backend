package org.ricky.common.validation.id.problem;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

import static org.ricky.common.constants.CommonConstants.PROBLEM_ID_PREFIX;
import static org.ricky.common.utils.ValidationUtils.isBlank;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/28
 * @className ProblemIdValidator
 * @desc
 */
@Deprecated
public class ProblemIdValidator implements ConstraintValidator<ProblemId, String> {

    private static final Pattern PATTERN = Pattern.compile("^" + PROBLEM_ID_PREFIX + "[0-9]{17,19}$");

    @Override
    public boolean isValid(String problemId, ConstraintValidatorContext constraintValidatorContext) {
        if (isBlank(problemId)) {
            return true;
        }
        return isProblemId(problemId);
    }

    public static boolean isProblemId(String problemId) {
        return PATTERN.matcher(problemId).matches();
    }

}
