package org.ricky.common.validation.id.group;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

import static org.ricky.common.constants.CommonConstants.GROUP_ID_PREFIX;
import static org.ricky.common.utils.ValidationUtils.isBlank;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/27
 * @className GroupIdValidator
 * @desc
 */
@Deprecated
public class GroupIdValidator implements ConstraintValidator<GroupId, String> {

    private static final Pattern PATTERN = Pattern.compile("^" + GROUP_ID_PREFIX + "[0-9]{17,19}$");

    @Override
    public boolean isValid(String groupId, ConstraintValidatorContext constraintValidatorContext) {
        if (isBlank(groupId)) {
            return true;
        }
        return isGroupId(groupId);
    }

    public static boolean isGroupId(String groupId) {
        return PATTERN.matcher(groupId).matches();
    }

}
