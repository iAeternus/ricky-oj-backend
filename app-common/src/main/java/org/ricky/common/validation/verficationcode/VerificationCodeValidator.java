package org.ricky.common.validation.verficationcode;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

import static org.ricky.common.constants.CommonRegexConstants.VERIFICATION_CODE_PATTERN;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/9/24
 * @className VerificationCodeValidator
 * @desc 验证码校验器
 */
public class VerificationCodeValidator implements ConstraintValidator<VerificationCode, String> {

    private static final Pattern PATTERN = Pattern.compile(VERIFICATION_CODE_PATTERN);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(value)) {
            return true;
        }

        return PATTERN.matcher(value).matches();
    }
}
