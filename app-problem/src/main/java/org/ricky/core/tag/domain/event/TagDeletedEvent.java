package org.ricky.core.tag.domain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.domain.event.DomainEvent;
import org.ricky.common.json.JsonTypeDefine;
import org.springframework.data.annotation.TypeAlias;

import static org.ricky.common.constants.CommonConstants.TAG_DELETED_EVENT;
import static org.ricky.common.domain.event.DomainEventTypeEnum.TAG_DELETED;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/5
 * @className TagDeletedEvent
 * @desc
 */
@Getter
@TypeAlias(TAG_DELETED_EVENT)
@JsonTypeDefine(TAG_DELETED_EVENT)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagDeletedEvent extends DomainEvent {

    private String tagId;

    public TagDeletedEvent(String tagId, UserContext userContext) {
        super(TAG_DELETED, userContext);
        this.tagId = tagId;
    }

}
