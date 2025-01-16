package org.ricky.core.problem.alter.command;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Command;
import org.ricky.core.problem.domain.setting.ProblemSetting;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/4
 * @className UpdateProblemSettingCommand
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateProblemSettingCommand implements Command {

    @Valid
    @NotNull
    ProblemSetting setting;

    @NotBlank
    String version;

}
