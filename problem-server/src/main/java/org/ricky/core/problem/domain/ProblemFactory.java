package org.ricky.core.problem.domain;

import org.ricky.common.context.UserContext;
import org.ricky.core.problem.alter.dto.command.CreateProblemCommand;
import org.springframework.stereotype.Component;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/2
 * @className ProblemFactory
 * @desc
 */
@Component
public class ProblemFactory {

    public Problem create(CreateProblemCommand command, UserContext userContext) {
        return new Problem(command.getCustomId(), command.getTitle(), command.getAuthor(), command.getDescription(),
                command.getInputFormat(), command.getOutputFormat(), command.getInputCases(), command.getOutputCases(),
                command.getHint(), userContext);
    }

}
