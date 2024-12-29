package org.ricky.core.user.domain.data.accepted;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Identified;
import org.ricky.common.validation.id.Id;

import static org.ricky.common.constants.CommonConstants.*;
import static org.ricky.common.utils.SnowflakeIdGenerator.newSnowflakeId;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/27
 * @className AcceptedProblem
 * @desc AC的题目
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AcceptedProblem implements Identified {

    @NotBlank
    @Schema(name = "ID")
    @Id(prefix = ACCEPTED_PROBLEM_ID_PREDIX, message = "Accepted Problem" + INCORRECT_ID_MESSAGE)
    String id;

    @NotBlank
    @Schema(name = "题目ID")
    @Id(prefix = PROBLEM_ID_PREFIX, message = "Problem" + INCORRECT_ID_MESSAGE)
    String problemId;

    @NotBlank
    @Schema(name = "提交ID")
    @Id(prefix = SUBMIT_ID_PREFIX, message = "Submit" + INCORRECT_ID_MESSAGE)
    String submitId;

    public static String newAcceptedProblemId() {
        return ACCEPTED_PROBLEM_ID_PREDIX + newSnowflakeId();
    }

    @Override
    public String getIdentifier() {
        return id;
    }
}
