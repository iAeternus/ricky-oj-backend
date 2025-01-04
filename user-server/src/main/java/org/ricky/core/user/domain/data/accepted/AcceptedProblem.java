package org.ricky.core.user.domain.data.accepted;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
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
@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AcceptedProblem implements Identified {

    /**
     * ID
     */
    @NotBlank
    @Id(prefix = ACCEPTED_PROBLEM_ID_PREDIX)
    private final String id;

    /**
     * 题目ID
     */
    @NotBlank
    @Id(prefix = PROBLEM_ID_PREFIX)
    private final String problemId;

    /**
     * 提交ID
     */
    @NotBlank
    @Id(prefix = SUBMIT_ID_PREFIX)
    private final String submitId;

    public AcceptedProblem(String problemId, String submitId) {
        this.id = newAcceptedProblemId();
        this.problemId = problemId;
        this.submitId = submitId;
    }

    public static String newAcceptedProblemId() {
        return ACCEPTED_PROBLEM_ID_PREDIX + newSnowflakeId();
    }

    @Override
    public String getIdentifier() {
        return id;
    }
}
