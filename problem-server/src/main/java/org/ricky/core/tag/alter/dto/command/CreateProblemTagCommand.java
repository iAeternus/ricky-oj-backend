package org.ricky.core.tag.alter.dto.command;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Command;
import org.ricky.common.validation.color.Color;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/5
 * @className CreateProblemTagCommand
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateProblemTagCommand implements Command {

    @NotBlank
    String name;

    @Color
    String color;

    String oj;

    String groupId;

}
