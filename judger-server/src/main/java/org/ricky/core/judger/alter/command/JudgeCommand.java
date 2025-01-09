package org.ricky.core.judger.alter.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Command;

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
}
