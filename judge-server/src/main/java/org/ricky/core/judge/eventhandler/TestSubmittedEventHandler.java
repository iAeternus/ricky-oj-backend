package org.ricky.core.judge.eventhandler;

import org.ricky.core.judge.domain.task.JudgeFailedTask;
import org.ricky.core.judge.domain.task.JudgeTask;
import org.ricky.core.judge.domain.task.RemoteJudgeTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/8
 * @className TestSubmittedEventHandler
 * @desc
 */
@Component
public class TestSubmittedEventHandler extends SubmittedEventHandler {

    @Autowired
    public TestSubmittedEventHandler(JudgeTask judgeTask,
                                     RemoteJudgeTask remoteJudgeTask,
                                     JudgeFailedTask judgeFailedTask) {
        super(judgeTask, remoteJudgeTask, judgeFailedTask);
    }

    @Override
    public int priority() {
        return 3;
    }

}
