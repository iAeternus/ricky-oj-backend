package org.ricky.core.judge.alter;

import lombok.RequiredArgsConstructor;
import org.ricky.client.ProblemClient;
import org.ricky.common.context.UserContext;
import org.ricky.common.ratelimit.RateLimiter;
import org.ricky.core.judge.alter.dto.command.SubmitCommand;
import org.ricky.core.judge.alter.dto.response.SubmitResponse;
import org.ricky.core.judge.domain.Judge;
import org.ricky.core.judge.domain.JudgeFactory;
import org.ricky.core.judge.domain.JudgeRepository;
import org.ricky.core.judge.domain.submit.Submit;
import org.ricky.dto.fetch.response.FetchProblemByIdResponse;
import org.ricky.dto.fetch.response.FetchSettingByIdResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.ricky.common.ratelimit.TPSConstants.NORMAL_TPS;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/6
 * @className JudgeAlterationService
 * @desc
 */
@Service
@RequiredArgsConstructor
public class JudgeAlterationService {

    private final RateLimiter rateLimiter;
    private final JudgeFactory judgeFactory;
    private final ProblemClient problemClient;
    private final JudgeRepository judgeRepository;

    @Transactional
    public SubmitResponse submit(SubmitCommand command, UserContext userContext) {
        rateLimiter.applyFor(userContext.getUserId(), "Judge:Submit", NORMAL_TPS);

        // 查询题目设置和题目信息
        FetchSettingByIdResponse setting = problemClient.fetchSettingById(command.getProblemId());
        setting.checkStatus();
        FetchProblemByIdResponse problem = problemClient.fetchById(command.getProblemId());

        // 评测信息落库
        Submit submit = judgeFactory.createSubmit(command, setting, problem, userContext);
        Judge judge = judgeFactory.createJudge(submit, userContext);
        judgeRepository.save(judge);
        judge.onSubmit(userContext);

        return SubmitResponse.builder()
                .judgeId(judge.getId())
                .build();
    }

}
