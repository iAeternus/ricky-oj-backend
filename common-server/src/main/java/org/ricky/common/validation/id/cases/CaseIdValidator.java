package org.ricky.common.validation.id.cases;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

import static org.ricky.common.constants.CommonConstants.CASE_ID_PREFIX;
import static org.ricky.common.utils.ValidationUtils.isBlank;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/27
 * @className CaseIdValidator
 * @desc
 */
@Deprecated
public class CaseIdValidator implements ConstraintValidator<CaseId, String> {

    private static final Pattern PATTERN = Pattern.compile("^" + CASE_ID_PREFIX + "[0-9]{17,19}$");

    @Override
    public boolean isValid(String caseId, ConstraintValidatorContext constraintValidatorContext) {
        if(isBlank(caseId)) {
            return true;
        }
        return isCaseId(caseId);
    }

    public static boolean isCaseId(String caseId) {
        return PATTERN.matcher(caseId).matches();
    }

}
