package org.ricky.dto.alter.command;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Command;
import org.ricky.common.validation.id.Id;
import org.ricky.dto.alter.JudgeStatusEnum;

import static org.ricky.common.constants.CommonConstants.JUDGE_ID_PREFIX;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/10
 * @className ModifyJudgeResultCommand
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ModifyJudgeResultCommand implements Command {

    @NotBlank
    @Id(prefix = JUDGE_ID_PREFIX)
    String judgeId;

    @NotNull
    JudgeStatusEnum status;

    @NotBlank
    String errorMessage;

    @Min(0)
    @NotNull
    Integer memory;

    @Min(0)
    @NotNull
    Integer time;

    // TODO oi score

}
