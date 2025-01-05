package org.ricky.core.tag.domain;

import org.ricky.common.context.UserContext;
import org.ricky.core.tag.alter.dto.command.CreateProblemTagCommand;
import org.springframework.stereotype.Component;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/5
 * @className TagFactory
 * @desc
 */
@Component
public class TagFactory {
    public Tag create(CreateProblemTagCommand command, UserContext userContext) {
        return new Tag(command.getName(), command.getColor(), command.getOj(), command.getGroupId(), userContext);
    }
}
