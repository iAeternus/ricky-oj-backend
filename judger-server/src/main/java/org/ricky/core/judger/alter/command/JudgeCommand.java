package org.ricky.core.judger.alter.command;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Command;
import org.ricky.common.validation.id.Id;

import static org.ricky.common.constants.CommonConstants.JUDGE_ID_PREFIX;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/9
 * @className JudgeCommand
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JudgeCommand implements Command {

    @NotBlank
    @Id(prefix = JUDGE_ID_PREFIX)
    String judgeId;

    @NotBlank
    String token;

}
