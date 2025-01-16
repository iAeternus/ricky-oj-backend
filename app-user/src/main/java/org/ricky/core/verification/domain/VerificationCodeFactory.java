package org.ricky.core.verification.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ricky.common.context.UserContext;
import org.ricky.common.exception.MyException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.ricky.common.exception.ErrorCodeEnum.TOO_MANY_VERIFICATION_CODE_FOR_TODAY;
import static org.ricky.common.exception.ErrorCodeEnum.VERIFICATION_CODE_ALREADY_SENT;
import static org.ricky.common.utils.CollectionUtils.mapOf;
import static org.ricky.core.common.utils.MobileOrEmailUtils.maskMobileOrEmail;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/9/24
 * @className VerificationCodeFactory
 * @desc
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class VerificationCodeFactory {

    private final VerificationCodeRepository verificationCodeRepository;

    public Optional<VerificationCode> create(String mobileOrEmail, VerificationCodeTypeEnum type, String userId, UserContext userContext) {
        try {
            if (verificationCodeRepository.existsWithinOneMinutes(mobileOrEmail, type)) {
                throw new MyException(VERIFICATION_CODE_ALREADY_SENT, "1分钟内只能获取一次验证码。",
                        mapOf("mobileOrEmail", maskMobileOrEmail(mobileOrEmail)));
            }

            if (verificationCodeRepository.totalCodeCountOfTodayFor(mobileOrEmail) > 20) {
                throw new MyException(TOO_MANY_VERIFICATION_CODE_FOR_TODAY, "验证码获取次数超过当天限制。",
                        mapOf("mobileOrEmail", maskMobileOrEmail(mobileOrEmail)));
            }

            return Optional.of(new VerificationCode(mobileOrEmail, type, userId, userContext));
        } catch (MyException ex) {
            log.warn("Error while create verification code: {}.", ex.getMessage());
            return Optional.empty();
        }
    }
}
