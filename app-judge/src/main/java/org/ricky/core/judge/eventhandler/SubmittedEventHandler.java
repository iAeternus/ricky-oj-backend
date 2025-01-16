package org.ricky.core.judge.eventhandler;

import lombok.extern.slf4j.Slf4j;
import org.ricky.common.domain.event.DomainEvent;
import org.ricky.common.domain.event.DomainEventHandler;
import org.ricky.common.utils.TaskRunner;
import org.ricky.core.judge.domain.event.SubmittedEvent;
import org.ricky.core.judge.domain.task.JudgeFailedTask;
import org.ricky.core.judge.domain.task.JudgeTask;
import org.ricky.core.judge.domain.task.RemoteJudgeTask;

import static org.ricky.common.domain.event.DomainEventTypeEnum.SUBMITTED;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/8
 * @className SubmittedEventHandler
 * @desc 优先处理比赛的提交任务 <br>
 * 其次处理普通提交的提交任务<br>
 * 最后处理在线调试的任务<br>
 */
@Slf4j
public abstract class SubmittedEventHandler implements DomainEventHandler {

    private final JudgeTask judgeTask;
    private final RemoteJudgeTask remoteJudgeTask;
    private final JudgeFailedTask judgeFailedTask;

    public SubmittedEventHandler(JudgeTask judgeTask,
                                 RemoteJudgeTask remoteJudgeTask,
                                 JudgeFailedTask judgeFailedTask) {
        this.judgeTask = judgeTask;
        this.remoteJudgeTask = remoteJudgeTask;
        this.judgeFailedTask = judgeFailedTask;
    }

    @Override
    public boolean canHandle(DomainEvent domainEvent) {
        return domainEvent.getType() == SUBMITTED;
    }

    @Override
    public void handle(DomainEvent domainEvent, TaskRunner taskRunner) {
        SubmittedEvent theEvent = (SubmittedEvent) domainEvent;
        if (theEvent.getIsRemote()) {
            taskRunner.run(() -> remoteJudgeTask.run(theEvent.getJudgeId(), theEvent.getProblemId(), theEvent.getCustomId(), theEvent.getSubmitType()));
        } else {
            taskRunner.run(() -> judgeTask.run(theEvent.getJudgeId(), theEvent.getProblemId(), theEvent.getSubmitType()));
        }
    }

    @Override
    public void handleFailure(DomainEvent domainEvent, TaskRunner taskRunner) {
        SubmittedEvent theEvent = (SubmittedEvent) domainEvent;
        taskRunner.run(() -> judgeFailedTask.run(theEvent.getJudgeId(), theEvent.getSubmitType()));
    }

}
