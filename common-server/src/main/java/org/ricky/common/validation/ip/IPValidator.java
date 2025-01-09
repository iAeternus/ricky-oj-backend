package org.ricky.common.validation.ip;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

import static org.ricky.common.constants.CommonRegexConstants.IP_PATTERN;
import static org.ricky.common.utils.ValidationUtils.isBlank;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/6
 * @className IPValidator
 * @desc
 */
public class IPValidator implements ConstraintValidator<IP, String> {

    private static final Pattern PATTERN = Pattern.compile(IP_PATTERN);

    @Override
    public boolean isValid(String ip, ConstraintValidatorContext constraintValidatorContext) {
        if (isBlank(ip)) {
            return true;
        }
        return isIP(ip);
    }

    public static boolean isIP(String ip) {
        return PATTERN.matcher(ip).matches();
    }

}
