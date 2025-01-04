package org.ricky.core.problem.domain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.domain.event.DomainEvent;
import org.ricky.common.domain.event.DomainEventTypeEnum;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/4
 * @className ProblemAwareDomainEvent
 * @desc
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ProblemAwareDomainEvent extends DomainEvent {

    private String problemId;

    public ProblemAwareDomainEvent(DomainEventTypeEnum type, String problemId, UserContext userContext) {
        super(type, userContext);
        this.problemId = problemId;
    }

}
