package org.ricky.core.problem.domain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.json.JsonTypeDefine;
import org.ricky.core.problem.domain.casegroup.cases.CaseInfo;
import org.springframework.data.annotation.TypeAlias;

import java.util.Set;

import static org.ricky.common.constants.CommonConstants.CASES_DELETED_EVENT;
import static org.ricky.common.domain.event.DomainEventTypeEnum.CASES_DELETED;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/4
 * @className CaseDeletedEvent
 * @desc
 */
@Getter
@TypeAlias(CASES_DELETED_EVENT)
@JsonTypeDefine(CASES_DELETED_EVENT)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CaseDeletedEvent extends ProblemAwareDomainEvent {

    private Set<CaseInfo> deletedCaseInfos;

    public CaseDeletedEvent(String problemId, Set<CaseInfo> deletedCaseInfos, UserContext userContext) {
        super(CASES_DELETED, problemId, userContext);
        this.deletedCaseInfos = deletedCaseInfos;
    }

}
