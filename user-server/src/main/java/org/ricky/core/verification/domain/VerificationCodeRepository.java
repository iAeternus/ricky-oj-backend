package org.ricky.core.verification.domain;

import java.util.Optional;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/9/23
 * @className VerificationCodeRepository
 * @desc
 */
public interface VerificationCodeRepository {
    Optional<VerificationCode> findValidOptional(String mobileOrEmail, String code, VerificationCodeTypeEnum type);

    void save(VerificationCode verificationCode);

    boolean existsWithinOneMinutes(String mobileOrEmail, VerificationCodeTypeEnum type);

    long totalCodeCountOfTodayFor(String mobileOrEmail);

    VerificationCode byId(String id);

    boolean exists(String id);
}
