package org.ricky.core.judge.fetch;

import lombok.RequiredArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.ratelimit.RateLimiter;
import org.ricky.core.judge.domain.Judge;
import org.ricky.core.judge.domain.JudgeRepository;
import org.ricky.core.judge.domain.result.JudgeResult;
import org.ricky.core.judge.fetch.response.FetchJudgeByIdResponse;
import org.springframework.stereotype.Service;

import static org.ricky.common.ratelimit.TPSConstants.NORMAL_TPS;
import static org.ricky.common.utils.ValidationUtils.nonNull;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/9
 * @className JudgeFetchService
 * @desc
 */
@Service
@RequiredArgsConstructor
public class JudgeFetchService {

    private final RateLimiter rateLimiter;
    private final JudgeRepository judgeRepository;

    public FetchJudgeByIdResponse fetchJudgeById(String judgeId, UserContext userContext) {
        rateLimiter.applyFor(userContext.getUserId(), "Judge:FetchJudgeById", NORMAL_TPS);

        Judge judge = judgeRepository.cachedById(judgeId);
        JudgeResult result = judge.getResult();

        return FetchJudgeByIdResponse.builder()
                .problemId(judge.getSubmit().getProblemId())
                .customId(judge.getSubmit().getCustomId())
                .nickname(judge.getSubmit().getNickname())
                .share(judge.getSubmit().getShare())
                .type(judge.getSubmit().getType())
                .isRemote(judge.getSubmit().getIsRemote())
                .program(judge.getSubmit().getProgram())
                .judgeCases(judge.getSubmit().getJudgeCases())
                .ip(judge.getIp())
                .submitAt(judge.getSubmitAt())
                .status(judge.getStatus())
                .errorMessage(nonNull(result) ? result.getErrorMessage() : null)
                .time(nonNull(result) ? result.getTime() : null)
                .memory(nonNull(result) ? result.getMemory() : null)
                .updatedAt(judge.getUpdatedAt())
                .build();
    }

}
