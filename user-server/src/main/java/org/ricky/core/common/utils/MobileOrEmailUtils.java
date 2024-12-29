package org.ricky.core.common.utils;

import static java.util.regex.Pattern.matches;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.ricky.common.constants.CommonRegexConstants.EMAIL_PATTERN;
import static org.ricky.common.constants.CommonRegexConstants.MOBILE_PATTERN;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/29
 * @className MobileOrEmailUtils
 * @desc
 */
public class MobileOrEmailUtils {

    public static boolean isMobileNumber(String value) {
        return matches(MOBILE_PATTERN, value);
    }

    public static boolean isEmail(String value) {
        return matches(EMAIL_PATTERN, value);
    }

    public static String maskMobileOrEmail(String mobileOrEmail) {
        if (isBlank(mobileOrEmail)) {
            return mobileOrEmail;
        }

        if (isMobileNumber(mobileOrEmail)) {
            return mobileOrEmail.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
        }

        return mobileOrEmail.replaceAll("(^[^@]{3}|(?!^)\\G)[^@]", "$1*");
    }

    public static String maskMobile(String mobile) {
        if (isBlank(mobile)) {
            return mobile;
        }

        return mobile.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
    }

}
