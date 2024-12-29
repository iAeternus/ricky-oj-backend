package org.ricky.common.validation.id.taggroup;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

import static org.ricky.common.constants.CommonConstants.TAG_GROUP_ID_PREFIX;
import static org.ricky.common.utils.ValidationUtils.isBlank;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/27
 * @className TagGroupIdValidator
 * @desc
 */
@Deprecated
public class TagGroupIdValidator implements ConstraintValidator<TagGroupId, String> {

    private static final Pattern PATTERN = Pattern.compile("^" + TAG_GROUP_ID_PREFIX + "[0-9]{17,19}$");

    @Override
    public boolean isValid(String tagGroupId, ConstraintValidatorContext constraintValidatorContext) {
        if (isBlank(tagGroupId)) {
            return true;
        }
        return isTagGroupId(tagGroupId);
    }

    public static boolean isTagGroupId(String tagGroupId) {
        return PATTERN.matcher(tagGroupId).matches();
    }

}
