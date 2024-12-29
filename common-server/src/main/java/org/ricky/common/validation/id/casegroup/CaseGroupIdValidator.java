package org.ricky.common.validation.id.casegroup;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

import static org.ricky.common.constants.CommonConstants.CASE_GROUP_ID_PREFIX;
import static org.ricky.common.utils.ValidationUtils.isBlank;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/27
 * @className CaseGroupIdValidator
 * @desc
 */
@Deprecated
public class CaseGroupIdValidator implements ConstraintValidator<CaseGroupId, String> {

    private static final Pattern PATTERN = Pattern.compile("^" + CASE_GROUP_ID_PREFIX + "[0-9]{17,19}$");

    @Override
    public boolean isValid(String caseGroupId, ConstraintValidatorContext constraintValidatorContext) {
        if (isBlank(caseGroupId)) {
            return true;
        }
        return isCaseGroupId(caseGroupId);
    }

    public static boolean isCaseGroupId(String caseGroupId) {
        return PATTERN.matcher(caseGroupId).matches();
    }

}
