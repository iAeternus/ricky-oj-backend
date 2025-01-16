package org.ricky.core.verification.fetch;

import lombok.RequiredArgsConstructor;
import org.ricky.common.ratelimit.RateLimiter;
import org.ricky.core.verification.domain.VerificationCode;
import org.ricky.core.verification.domain.VerificationCodeRepository;
import org.ricky.core.verification.fetch.response.FetchVerificationByIdResponse;
import org.springframework.stereotype.Service;

import static org.ricky.common.ratelimit.TPSConstants.NORMAL_TPS;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/30
 * @className VerificationCodeFetchService
 * @desc
 */
@Service
@RequiredArgsConstructor
public class VerificationCodeFetchService {

    private final RateLimiter rateLimiter;
    private final VerificationCodeRepository verificationCodeRepository;

    public FetchVerificationByIdResponse fetchById(String verificationCodeId) {
        rateLimiter.applyFor("VerificationCode:FetchById", NORMAL_TPS);

        VerificationCode verificationCode = verificationCodeRepository.byId(verificationCodeId);
        return FetchVerificationByIdResponse.builder()
                .code(verificationCode.getCode())
                .build();
    }
}
