package org.ricky.common.constants;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/27
 * @className CommonRegexConstants
 * @desc
 */
public interface CommonRegexConstants {

    String RGBA_COLOR_PATTERN = "^rgba\\(\\s*(0|[1-9]\\d?|1\\d\\d?|2[0-4]\\d|25[0-5])%?\\s*,\\s*(0|[1-9]\\d?|1\\d\\d?|2[0-4]\\d|25[0-5])%?\\s*,\\s*(0|[1-9]\\d?|1\\d\\d?|2[0-4]\\d|25[0-5])%?\\s*,\\s*((0.[0-9]{0,2})|[01]|(.[0-9]{1,2}))\\s*\\)$";
    String HEX_COLOR_PATTERN = "^#[0-9a-f]{3}([0-9a-f]{3})?$";
    String RGB_COLOR_PATTERN = "^rgb\\(\\s*(0|[1-9]\\d?|1\\d\\d?|2[0-4]\\d|25[0-5])%?\\s*,\\s*(0|[1-9]\\d?|1\\d\\d?|2[0-4]\\d|25[0-5])%?\\s*,\\s*(0|[1-9]\\d?|1\\d\\d?|2[0-4]\\d|25[0-5])%?\\s*\\)$";

}
