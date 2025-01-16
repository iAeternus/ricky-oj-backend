package org.ricky.core.problem.domain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.json.JsonTypeDefine;
import org.ricky.core.problem.domain.casegroup.cases.CaseInfo;
import org.springframework.data.annotation.TypeAlias;

import java.util.Set;

import static org.ricky.common.constants.CommonConstants.CASES_ADDED_EVENT;
import static org.ricky.common.domain.event.DomainEventTypeEnum.CASES_DELETED;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/16
 * @className CaseAddedEvent
 * @desc
 */
@Getter
@TypeAlias(CASES_ADDED_EVENT)
@JsonTypeDefine(CASES_ADDED_EVENT)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CaseAddedEvent extends ProblemAwareDomainEvent {

    private Set<CaseInfo> addedCaseInfos;

    public CaseAddedEvent(String problemId, Set<CaseInfo> addedCaseInfos, UserContext userContext) {
        super(CASES_DELETED, problemId, userContext);
        this.addedCaseInfos = addedCaseInfos;
    }

}
