package org.ricky.core.judge.domain.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ricky.common.domain.task.RepeatableTask;
import org.ricky.core.judge.domain.Judge;
import org.ricky.core.judge.domain.JudgeRepository;
import org.ricky.core.judge.domain.submit.SubmitTypeEnum;
import org.springframework.stereotype.Component;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/8
 * @className JudgeFailedTask
 * @desc 评测失败
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JudgeFailedTask implements RepeatableTask {

    private final JudgeRepository judgeRepository;

    public void run(String judgeId, SubmitTypeEnum submitType) {
        Judge judge = judgeRepository.cachedById(judgeId);
        judge.judgeFailed();
        judgeRepository.save(judge);

        log.info("submitType: {}", submitType); // TODO
    }
}
