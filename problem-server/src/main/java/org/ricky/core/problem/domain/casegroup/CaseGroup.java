package org.ricky.core.problem.domain.casegroup;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.ricky.common.domain.marker.Identified;
import org.ricky.common.validation.collection.NoNullElement;
import org.ricky.common.validation.id.Id;
import org.ricky.core.problem.domain.casegroup.cases.Case;

import java.util.List;

import static org.ricky.common.constants.CommonConstants.*;
import static org.ricky.common.utils.SnowflakeIdGenerator.newSnowflakeId;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/27
 * @className CaseGroup
 * @desc 测试用例组
 */
@Value
@Builder
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CaseGroup implements Identified {

    @NotBlank
    @Schema(name = "ID")
    @Id(prefix = CASE_GROUP_ID_PREFIX)
    String id;

    @Schema(name = "组名")
    String name;

    @Schema(name = "组计分模式")
    CaseGroupModeEnum mode;

    @Valid
    @NotNull
    @NoNullElement
    @Size(max = MAX_CASES_SIZE)
    @Schema(name = "测试用例集合")
    List<Case> cases;

    public static String newCaseGroupId() {
        return CASE_GROUP_ID_PREFIX + newSnowflakeId();
    }

    @Override
    public String getIdentifier() {
        return id;
    }
}
