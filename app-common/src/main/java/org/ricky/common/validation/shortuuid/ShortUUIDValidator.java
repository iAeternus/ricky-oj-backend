package org.ricky.common.validation.shortuuid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;


/**
 * @author Ricky
 * @version 1.0
 * @date 2024/9/9
 * @className ShortUUIDValidator
 * @desc UUID校验器
 */
public class ShortUUIDValidator implements ConstraintValidator<ShortUUID, String> {
    @Override
    public boolean isValid(String uuid, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(uuid)) {
            return true;
        }

        if (uuid.length() < 20 || uuid.length() > 23) {
            return false;
        }

        return Base64.isBase64(uuid);
    }
}
