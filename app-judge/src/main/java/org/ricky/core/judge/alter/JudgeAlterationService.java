package org.ricky.core.judge.alter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ricky.client.ProblemClient;
import org.ricky.common.context.UserContext;
import org.ricky.common.ratelimit.RateLimiter;
import org.ricky.core.judge.alter.command.ModifyJudgeResultCommand;
import org.ricky.core.judge.alter.command.ModifyStatusCommand;
import org.ricky.core.judge.alter.command.SubmitCommand;
import org.ricky.core.judge.alter.response.SubmitResponse;
import org.ricky.core.judge.domain.Judge;
import org.ricky.core.judge.domain.JudgeFactory;
import org.ricky.core.judge.domain.JudgeRepository;
import org.ricky.core.judge.domain.submit.Submit;
import org.ricky.dto.fetch.response.ProblemInfo;
import org.ricky.dto.fetch.response.ProblemSetting;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.ricky.common.ratelimit.TPSConstants.HIGH_TPS;
import static org.ricky.common.ratelimit.TPSConstants.NORMAL_TPS;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/6
 * @className JudgeAlterationService
 * @desc
 */
@Slf4j
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
        ProblemSetting setting = problemClient.fetchSettingById(command.getProblemId());
        setting.checkStatus();
        ProblemInfo problem = problemClient.fetchById(command.getProblemId());

        // 评测信息落库
        Submit submit = judgeFactory.createSubmit(command, setting, problem, userContext);
        Judge judge = judgeFactory.createJudge(submit, userContext);
        judgeRepository.save(judge);
        judge.onSubmit(userContext);
        log.info("Submit[{}] complete successfully.", judge.getId());

        return SubmitResponse.builder()
                .judgeId(judge.getId())
                .build();
    }

    @Transactional
    public void modifyStatus(ModifyStatusCommand command, UserContext userContext) {
        rateLimiter.applyFor(userContext.getUserId(), "Judge:ModifyStatus", HIGH_TPS);

        Judge judge = judgeRepository.cachedById(command.getJudgeId());
        if (judge.getStatus() == command.getNewStatus()) {
            return;
        }
        judge.modifyStatus(command.getNewStatus(), userContext);
        judgeRepository.save(judge);
        log.info("Judge[{}] to modify status to [{}].", command.getJudgeId(), command.getNewStatus());
    }

    @Transactional
    public void modifyJudgeResult(ModifyJudgeResultCommand command, UserContext userContext) {
        rateLimiter.applyFor(userContext.getUserId(), "Judge:ModifyJudgeResult", NORMAL_TPS);

        Judge judge = judgeRepository.cachedById(command.getJudgeId());
        judge.checkJudgeNotOver();
        judge.setJudgeResult(command.getStatus(), command.getErrorMessage(), command.getMemory(), command.getTime(), userContext);
        judgeRepository.save(judge);
        log.info("Judge[{}] modify the result.", command.getJudgeId());
    }
}
