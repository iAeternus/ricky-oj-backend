package org.ricky.core.judger.alter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ricky.client.JudgeClient;
import org.ricky.client.ProblemClient;
import org.ricky.common.context.UserContext;
import org.ricky.common.ratelimit.RateLimiter;
import org.ricky.core.judger.alter.command.JudgeCommand;
import org.ricky.core.judger.alter.response.JudgeResponse;
import org.ricky.core.judger.domain.JudgerDomainService;
import org.ricky.core.judger.domain.context.JudgeResult;
import org.ricky.dto.alter.command.ModifyJudgeResultCommand;
import org.ricky.dto.alter.command.ModifyStatusCommand;
import org.ricky.dto.alter.response.Judge;
import org.ricky.dto.fetch.response.ProblemSetting;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.ricky.common.ratelimit.TPSConstants.NORMAL_TPS;
import static org.ricky.dto.alter.JudgeStatusEnum.COMPILING;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/9
 * @className JudgerAlterationService
 * @desc
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JudgerAlterationService {

    private final RateLimiter rateLimiter;
    private final JudgeClient judgeClient;
    private final ProblemClient problemClient;
    private final JudgerDomainService judgerDomainService;

    @Transactional
    public JudgeResponse judge(JudgeCommand command, UserContext userContext) {
        rateLimiter.applyFor(userContext.getUserId(), "Judger:Judge", NORMAL_TPS);

        // 修改评测状态为编译
        judgeClient.modifyStatus(ModifyStatusCommand.builder()
                .judgeId(command.getJudgeId())
                .newStatus(COMPILING)
                .build());

        // 数据准备
        Judge judge = judgeClient.fetchJudgeById(command.getJudgeId());
        ProblemSetting problemSetting = problemClient.fetchSettingById(judge.getProblemId());

        // 评测
        JudgeResult result = judgerDomainService.judge(problemSetting, judge);

        // 评测结果落库
        judgeClient.modifyJudgeResult(ModifyJudgeResultCommand.builder()
                .judgeId(result.getJudgeId())
                .errorMessage(result.getErrorMessage())
                .memory(result.getMemory())
                .time(result.getTime())
                .build());
        log.info("Judge[{}] complete successfully.", result.getJudgeId());

        return JudgeResponse.builder()
                .status(result.getStatus())
                .build();
    }
}
