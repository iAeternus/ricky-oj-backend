package org.ricky.core.judge.domain.submit.judgecase;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.ricky.common.domain.UploadedFile;
import org.ricky.common.domain.marker.Identified;
import org.ricky.common.validation.id.Id;
import org.ricky.core.judge.domain.JudgeStatusEnum;

import static org.ricky.common.constants.CommonConstants.CASE_ID_PREFIX;
import static org.ricky.common.constants.CommonConstants.JUDGE_CASE_ID_PREFIX;
import static org.ricky.common.utils.SnowflakeIdGenerator.newSnowflakeId;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/6
 * @className JudgeCase
 * @desc 评测样例
 */
@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JudgeCase implements Identified {

    /**
     * ID
     */
    @NotBlank
    @Id(prefix = JUDGE_CASE_ID_PREFIX)
    private String id;

    /**
     * 测试用例ID
     */
    @NotBlank
    @Id(prefix = CASE_ID_PREFIX)
    private String caseId;

    /**
     * 该测试样例所用时间(ms)
     */
    private Integer time;

    /**
     * 该测试样例所用空间(KB)
     */
    private Integer memory;

    /**
     * 该测试样例状态
     */
    @NotNull
    private JudgeStatusEnum status;

    /**
     * 样例输入文件
     */
    @Valid
    @NotNull
    private UploadedFile input;

    /**
     * 样例输出文件
     */
    @Valid
    @NotNull
    private UploadedFile output;

    /**
     * 用户样例输出文件
     */
    @Valid
    private UploadedFile userOutput;

    /**
     * 排序
     */
    @NotNull
    private Integer seq;

    public static String newJudgeCaseId() {
        return JUDGE_CASE_ID_PREFIX + newSnowflakeId();
    }

    @Override
    public String getIdentifier() {
        return null;
    }
}
