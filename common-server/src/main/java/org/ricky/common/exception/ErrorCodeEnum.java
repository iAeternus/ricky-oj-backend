package org.ricky.common.exception;

import lombok.Getter;


/**
 * @author Ricky
 * @version 1.0
 * @date 2024/9/9
 * @className ErrorCodeEnum
 * @desc 错误码
 * 400：请求验证错误，请求中资源所属关系错误等
 * 401：认证错误
 * 403：权限不够
 * 404：资源未找到
 * 409：业务异常
 * 426：套餐检查失败
 * 500：系统错误
 */
@Getter
public enum ErrorCodeEnum {

    // 400
    REQUEST_VALIDATION_FAILED(400),
    INVALID_EPS(400),
    INVALID_ENUM_KEY(400),

    // 401
    AUTHENTICATION_FAILED(401),

    // 403
    ACCESS_DENIED(403),
    WRONG_USER(403),

    // 404
    NOT_FOUND(404),
    AR_NOT_FOUND(404),
    AR_NOT_FOUND_ALL(404),
    DOMAIN_EVENT_NOT_FOUND(404),

    // 409
    VERIFICATION_CODE_COUNT_OVERFLOW(409),
    VERIFICATION_CODE_CHECK_FAILED(409),
    VERIFICATION_CODE_ALREADY_SENT(409),
    TOO_MANY_VERIFICATION_CODE_FOR_TODAY(409),
    MUST_SIGN_AGREEMENT(409),
    USER_WITH_MOBILE_OR_EMAIL_ALREADY_EXISTS(409),
    USER_ALREADY_LOCKED(409),
    USER_ALREADY_DEACTIVATED(409),
    INPUT_OUTPUT_NOT_MATCH(409),
    ACM_FORMAT_DOES_NOT_NEED_SCORE(409),
    PROBLEM_WITH_CUSTOM_ID_ALREADY_EXISTS(409),
    REQUIRE_GROUP_ID(409),
    NOT_GROUPED_PROBLEM(409),
    LACK_OF_PROBLEM_SOURCE(409),
    DO_NOT_SUPPORT_REMOTE(409),
    PROBLEM_ALREADY_UPDATED(409),
    CASE_GROUP_ID_DUPLICATED(409),
    MUST_HAVE_CASES(409),

    // 426

    // 429
    TOO_MANY_REQUEST(429),

    // 500
    SYSTEM_ERROR(500),
    ;

    private final int status;

    ErrorCodeEnum(int status) {
        this.status = status;
    }

}
