package org.ricky.core.problem.domain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.json.JsonTypeDefine;
import org.ricky.core.problem.domain.casegroup.CaseGroupInfo;
import org.springframework.data.annotation.TypeAlias;

import java.util.Set;

import static org.ricky.common.constants.CommonConstants.CASE_GROUPS_ADDED_EVENT;
import static org.ricky.common.domain.event.DomainEventTypeEnum.CASES_DELETED;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/16
 * @className CaseGroupAddedEvent
 * @desc
 */
@Getter
@TypeAlias(CASE_GROUPS_ADDED_EVENT)
@JsonTypeDefine(CASE_GROUPS_ADDED_EVENT)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CaseGroupAddedEvent extends ProblemAwareDomainEvent {

    private Set<CaseGroupInfo> addedCaseGroupInfos;

    public CaseGroupAddedEvent(String problemId, Set<CaseGroupInfo> addedCaseGroupInfos, UserContext userContext) {
        super(CASES_DELETED, problemId, userContext);
        this.addedCaseGroupInfos = addedCaseGroupInfos;
    }

}
