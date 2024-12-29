package org.ricky.common.validation.id.tag;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

import static org.ricky.common.constants.CommonConstants.TAG_ID_PREFIX;
import static org.ricky.common.utils.ValidationUtils.isBlank;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/27
 * @className TagGroupIdValidator
 * @desc
 */
@Deprecated
public class TagIdValidator implements ConstraintValidator<TagId, String> {

    private static final Pattern PATTERN = Pattern.compile("^" + TAG_ID_PREFIX + "[0-9]{17,19}$");

    @Override
    public boolean isValid(String tagId, ConstraintValidatorContext constraintValidatorContext) {
        if(isBlank(tagId)) {
            return true;
        }
        return isTagId(tagId);
    }

    public static boolean isTagId(String tagId) {
        return PATTERN.matcher(tagId).matches();
    }

}
