package org.ricky.core.problem.domain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.json.JsonTypeDefine;
import org.ricky.core.problem.domain.casegroup.CaseGroupInfo;
import org.springframework.data.annotation.TypeAlias;

import java.util.Set;

import static org.ricky.common.constants.CommonConstants.CASE_GROUPS_DELETED_EVENT;
import static org.ricky.common.domain.event.DomainEventTypeEnum.CASE_GROUPS_DELETED;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/4
 * @className ProblemCaseGroupDeletedEvent
 * @desc
 */
@Getter
@TypeAlias(CASE_GROUPS_DELETED_EVENT)
@JsonTypeDefine(CASE_GROUPS_DELETED_EVENT)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProblemCaseGroupDeletedEvent extends ProblemAwareDomainEvent {

    private Set<CaseGroupInfo> caseGroupInfos;

    public ProblemCaseGroupDeletedEvent(String problemId, Set<CaseGroupInfo> caseGroupInfos, UserContext userContext) {
        super(CASE_GROUPS_DELETED, problemId, userContext);
        this.caseGroupInfos = caseGroupInfos;
    }
}
