package org.ricky.core.user.domain.event;

import lombok.*;
import org.ricky.common.context.UserContext;
import org.ricky.common.domain.event.DomainEvent;
import org.ricky.common.json.JsonTypeDefine;
import org.springframework.data.annotation.TypeAlias;

import static org.ricky.common.constants.CommonConstants.USER_CREATED_EVENT;
import static org.ricky.common.domain.event.DomainEventTypeEnum.USER_CREATED;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/4
 * @className UserCreatedEvent
 * @desc
 */
@Getter
@TypeAlias(USER_CREATED_EVENT)
@JsonTypeDefine(USER_CREATED_EVENT)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCreatedEvent extends DomainEvent {

    private String userId;

    public UserCreatedEvent(String userId, UserContext userContext) {
        super(USER_CREATED, userContext);
        this.userId = userId;
    }
}
