package org.ricky.core.judger.fetch;

import lombok.RequiredArgsConstructor;
import org.ricky.common.ratelimit.RateLimiter;
import org.ricky.core.judger.domain.StartupRunner;
import org.ricky.core.judger.fetch.dto.response.FetchJudgerInfoResponse;
import org.springframework.stereotype.Service;

import static org.ricky.common.ratelimit.TPSConstants.EXTREMELY_LOW_TPS;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/7
 * @className JudgerFetchService
 * @desc
 */
@Service
@RequiredArgsConstructor
public class JudgerFetchService {

    private final RateLimiter rateLimiter;
    private final StartupRunner startupRunner;

    public FetchJudgerInfoResponse fetchJudgerInfo() {
        rateLimiter.applyFor("Judger:FetchJudgerInfo", EXTREMELY_LOW_TPS);

        return FetchJudgerInfoResponse.builder()
                .info(startupRunner.fetchJudgerInfo())
                .build();
    }
}
