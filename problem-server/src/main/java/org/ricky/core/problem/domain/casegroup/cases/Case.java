package org.ricky.core.problem.domain.casegroup.cases;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.ricky.common.domain.marker.Identified;
import org.ricky.common.validation.id.Id;

import static org.ricky.common.constants.CommonConstants.CASE_ID_PREFIX;
import static org.ricky.common.utils.SnowflakeIdGenerator.newSnowflakeId;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/27
 * @className Case
 * @desc 测试用例
 */
@Value
@Builder
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Case implements Identified {

    @NotBlank
    @Schema(name = "ID")
    @Id(prefix = CASE_ID_PREFIX)
    String id;

    @NotBlank
    @Schema(name = "测试用例的输入")
    String input;

    @NotBlank
    @Schema(name = "测试用例的输出")
    String output;

    @Schema(name = "该测试样例的OI得分")
    Integer oiScore;

    @Schema(name = "排序")
    int seq;

    public static String newCaseId() {
        return CASE_ID_PREFIX + newSnowflakeId();
    }

    @Override
    public String getIdentifier() {
        return id;
    }
}
