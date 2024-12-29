package org.ricky.common.validation.mobileoremail;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.ricky.common.constants.CommonRegexConstants;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/9/9
 * @className MobileOrEmailValidator
 * @desc
 */
public class MobileOrEmailValidator implements ConstraintValidator<MobileOrEmail, String> {
    private static final Pattern MOBILE_PATTERN = Pattern.compile(CommonRegexConstants.MOBILE_PATTERN);
    private static final Pattern EMAIL_PATTERN = Pattern.compile(CommonRegexConstants.EMAIL_PATTERN, CASE_INSENSITIVE);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (isBlank(value)) {
            return true;
        }

        return value.length() <= 50 && (MOBILE_PATTERN.matcher(value).matches() || EMAIL_PATTERN.matcher(value).matches());
    }
}
