package org.ricky.core.judge.domain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.domain.event.DomainEvent;
import org.ricky.common.json.JsonTypeDefine;
import org.ricky.core.judge.domain.submit.SubmitTypeEnum;
import org.springframework.data.annotation.TypeAlias;

import static org.ricky.common.constants.CommonConstants.SUBMITTED_EVENT;
import static org.ricky.common.domain.event.DomainEventTypeEnum.SUBMITTED;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/8
 * @className SubmittedEvent
 * @desc
 */
@Getter
@TypeAlias(SUBMITTED_EVENT)
@JsonTypeDefine(SUBMITTED_EVENT)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubmittedEvent extends DomainEvent {

    private String judgeId;
    private String problemId;
    private String customId;
    private SubmitTypeEnum submitType;
    private Boolean isRemote;

    public SubmittedEvent(String judgeId, String problemId, String customId, SubmitTypeEnum submitType, Boolean isRemote, UserContext userContext) {
        super(SUBMITTED, userContext);
        this.judgeId = judgeId;
        this.problemId = problemId;
        this.customId = customId;
        this.submitType = submitType;
        this.isRemote = isRemote;
    }
}
