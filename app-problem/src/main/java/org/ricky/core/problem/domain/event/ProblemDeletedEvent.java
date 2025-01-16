package org.ricky.core.problem.domain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.json.JsonTypeDefine;
import org.springframework.data.annotation.TypeAlias;

import static org.ricky.common.constants.CommonConstants.PROBLEM_DELETED_EVENT;
import static org.ricky.common.domain.event.DomainEventTypeEnum.PROBLEM_DELETED;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/6
 * @className ProblemDeletedEvent
 * @desc
 */
@Getter
@TypeAlias(PROBLEM_DELETED_EVENT)
@JsonTypeDefine(PROBLEM_DELETED_EVENT)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProblemDeletedEvent extends ProblemAwareDomainEvent {

    public ProblemDeletedEvent(String problemId, UserContext userContext) {
        super(PROBLEM_DELETED, problemId, userContext);
    }

}
