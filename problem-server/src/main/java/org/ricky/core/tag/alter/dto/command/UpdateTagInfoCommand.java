package org.ricky.core.tag.alter.dto.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Command;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/5
 * @className UpdateTagInfoCommand
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateTagInfoCommand implements Command {

    String name;
    String color;
    String oj;
    String groupId;

}
