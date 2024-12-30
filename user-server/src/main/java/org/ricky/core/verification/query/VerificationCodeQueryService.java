package org.ricky.core.verification.query;

import lombok.RequiredArgsConstructor;
import org.ricky.common.ratelimit.RateLimiter;
import org.ricky.core.verification.domain.VerificationCode;
import org.ricky.core.verification.domain.VerificationCodeRepository;
import org.ricky.core.verification.query.dto.response.FetchByIdResponse;
import org.springframework.stereotype.Service;

import static org.ricky.common.ratelimit.TPSConstants.NORMAL_TPS;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/30
 * @className VerificationCodeQueryService
 * @desc
 */
@Service
@RequiredArgsConstructor
public class VerificationCodeQueryService {

    private final RateLimiter rateLimiter;
    private final VerificationCodeRepository verificationCodeRepository;

    public FetchByIdResponse fetchById(String verificationCodeId) {
        rateLimiter.applyFor("VerificationCode:FetchById", NORMAL_TPS);

        VerificationCode verificationCode = verificationCodeRepository.byId(verificationCodeId);
        return FetchByIdResponse.builder()
                .code(verificationCode.getCode())
                .build();
    }
}
