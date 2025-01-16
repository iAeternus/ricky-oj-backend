package org.ricky.core.judge.alter.command;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Command;
import org.ricky.common.validation.id.Id;
import org.ricky.core.judge.domain.JudgeStatusEnum;

import static org.ricky.common.constants.CommonConstants.JUDGE_ID_PREFIX;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/9
 * @className ModifyStatusCommand
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ModifyStatusCommand implements Command {

    @Id(prefix = JUDGE_ID_PREFIX)
    String judgeId;

    @NotNull
    JudgeStatusEnum newStatus;

}
