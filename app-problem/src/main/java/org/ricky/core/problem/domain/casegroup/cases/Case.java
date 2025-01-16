package org.ricky.core.problem.domain.casegroup.cases;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.ricky.common.domain.UploadedFile;
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
@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Case implements Identified {

    /**
     * ID
     */
    @NotBlank
    @Id(prefix = CASE_ID_PREFIX)
    private final String id;

    /**
     * 测试用例的输入文件
     */
    @NotBlank
    private UploadedFile input;

    /**
     * 测试用例的输出文件
     */
    @NotBlank
    private UploadedFile output;

    /**
     * 排序
     */
    @NotNull
    private Integer seq;

    public static String newCaseId() {
        return CASE_ID_PREFIX + newSnowflakeId();
    }

    @Override
    public String getIdentifier() {
        return id;
    }
}
