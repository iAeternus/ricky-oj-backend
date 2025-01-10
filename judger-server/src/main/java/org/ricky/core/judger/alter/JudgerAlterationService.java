package org.ricky.core.judger.alter;

import lombok.RequiredArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.ratelimit.RateLimiter;
import org.ricky.common.ratelimit.TPSConstants;
import org.ricky.core.judger.alter.command.JudgeCommand;
import org.ricky.core.judger.alter.response.JudgeResponse;
import org.springframework.stereotype.Service;

import static org.ricky.common.ratelimit.TPSConstants.NORMAL_TPS;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/9
 * @className JudgerAlterationService
 * @desc
 */
@Service
@RequiredArgsConstructor
public class JudgerAlterationService {

    private final RateLimiter rateLimiter;

    public JudgeResponse judge(JudgeCommand command, UserContext userContext) {
        rateLimiter.applyFor(userContext.getUserId(), "Judger:Judge", NORMAL_TPS);

        // TODO
        return null;
    }
}
