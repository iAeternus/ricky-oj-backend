package org.ricky.core.judge.domain.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ricky.common.domain.task.RepeatableTask;
import org.ricky.core.judge.domain.submit.SubmitTypeEnum;
import org.springframework.stereotype.Component;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/8
 * @className JudgeTask
 * @desc 评测，允许重试
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JudgeTask implements RepeatableTask {

    public void run(String judgeId, String problemId, SubmitTypeEnum submitType) {
        log.info("judge");
        // TODO 调用judger-server
    }

}
