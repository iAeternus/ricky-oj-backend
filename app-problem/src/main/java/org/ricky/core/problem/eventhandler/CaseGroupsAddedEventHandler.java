package org.ricky.core.problem.eventhandler;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ricky.common.domain.event.DomainEvent;
import org.ricky.common.domain.event.DomainEventHandler;
import org.ricky.common.domain.event.DomainEventTypeEnum;
import org.ricky.common.utils.TaskRunner;
import org.ricky.core.problem.domain.casegroup.CaseGroupInfo;
import org.ricky.core.problem.domain.event.CaseGroupAddedEvent;
import org.ricky.core.problem.domain.task.AddCaseGroupFailedTask;
import org.ricky.core.problem.domain.task.AddCaseGroupTask;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static org.ricky.common.constants.CommonConstants.CASE_GROUPS_ADDED_EVENT;
import static org.ricky.common.domain.event.DomainEventTypeEnum.CASE_GROUPS_ADDED;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/16
 * @className CaseGroupsAddedEventHandler
 * @desc
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CaseGroupsAddedEventHandler implements DomainEventHandler {

    private final AddCaseGroupTask addCaseGroupTask;
    private final AddCaseGroupFailedTask addCaseGroupFailedTask;

    @Override
    public boolean canHandle(DomainEvent domainEvent) {
        return domainEvent.getType() == CASE_GROUPS_ADDED;
    }

    @Override
    public void handle(DomainEvent domainEvent, TaskRunner taskRunner) {
        CaseGroupAddedEvent theEvent = (CaseGroupAddedEvent) domainEvent;
        List<String> caseGroupIds = theEvent.getAddedCaseGroupInfos().stream()
                .map(CaseGroupInfo::getCaseGroupId)
                .collect(toImmutableList());
        taskRunner.run(() -> addCaseGroupTask.run(theEvent.getProblemId(), caseGroupIds));
    }

    @Override
    public void handleFailure(DomainEvent domainEvent, TaskRunner taskRunner) {
        CaseGroupAddedEvent theEvent = (CaseGroupAddedEvent) domainEvent;
        List<String> caseGroupIds = theEvent.getAddedCaseGroupInfos().stream()
                .map(CaseGroupInfo::getCaseGroupId)
                .collect(toImmutableList());
        taskRunner.run(() -> addCaseGroupFailedTask.run(theEvent.getProblemId(), caseGroupIds));
    }
}
