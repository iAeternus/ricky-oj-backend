package org.ricky.core.register.alter.dto.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Command;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/29
 * @className RegisterCommand
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterCommand implements Command {
}
