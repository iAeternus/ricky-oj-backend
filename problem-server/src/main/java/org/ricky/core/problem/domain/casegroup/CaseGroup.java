package org.ricky.core.problem.domain.casegroup;

import com.google.common.collect.ImmutableSet;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.ricky.common.domain.marker.Identified;
import org.ricky.common.exception.MyException;
import org.ricky.common.utils.ValidationUtils;
import org.ricky.common.validation.collection.NoNullElement;
import org.ricky.common.validation.id.Id;
import org.ricky.core.problem.domain.casegroup.cases.Case;
import org.ricky.core.problem.domain.casegroup.cases.CaseInfo;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static org.ricky.common.constants.CommonConstants.*;
import static org.ricky.common.exception.ErrorCodeEnum.MUST_HAVE_CASES;
import static org.ricky.common.utils.CollectionUtils.mapOf;
import static org.ricky.common.utils.SnowflakeIdGenerator.newSnowflakeId;
import static org.ricky.common.utils.ValidationUtils.isEmpty;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/27
 * @className CaseGroup
 * @desc 测试用例组
 */
@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CaseGroup implements Identified {

    /**
     * ID
     */
    @NotBlank
    @Id(prefix = CASE_GROUP_ID_PREFIX)
    private final String id;

    /**
     * 组名
     */
    private final String name;

    /**
     * 测试用例集合
     */
    @Valid
    @NotNull
    @NoNullElement
    @Size(max = MAX_CASES_SIZE)
    private List<Case> cases;

    public static String newCaseGroupId() {
        return CASE_GROUP_ID_PREFIX + newSnowflakeId();
    }

    public CaseGroupInfo toCaseGroupInfo() {
        Set<CaseInfo> caseInfos = cases.stream()
                .map(caze -> CaseInfo.builder()
                        .caseGroupId(getId())
                        .caseId(caze.getId())
                        .build())
                .collect(toImmutableSet());

        return CaseGroupInfo.builder()
                .caseGroupId(getId())
                .caseInfos(caseInfos)
                .build();
    }

    public void validate() {
        if(isEmpty(cases)) {
            throw new MyException(MUST_HAVE_CASES, "The test group cannot be without test cases.",
                    mapOf("caseGroupId", getId()));
        }
    }

    @Override
    public String getIdentifier() {
        return id;
    }

}
