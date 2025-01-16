package org.ricky.core.judge.domain.submit;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.ValueObject;
import org.ricky.common.domain.program.Program;
import org.ricky.common.validation.collection.NoNullElement;
import org.ricky.common.validation.id.Id;
import org.ricky.core.judge.domain.submit.judgecase.JudgeCase;

import java.util.List;

import static org.ricky.common.constants.CommonConstants.MAX_CASES_SIZE;
import static org.ricky.common.constants.CommonConstants.PROBLEM_ID_PREFIX;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/7
 * @className Submit
 * @desc 提交信息
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Submit implements ValueObject {

    /**
     * 题目ID
     */
    @NotBlank
    @Id(prefix = PROBLEM_ID_PREFIX)
    String problemId;

    /**
     * 题目自定义ID，例如：ROJ-1
     */
    @NotBlank
    String customId;

    /**
     * 用户昵称
     */
    @NotBlank
    String nickname;

    /**
     * true=全部人可见 false=仅自己可见
     */
    @NotNull
    Boolean share;

    /**
     * 提交类型
     */
    @NotNull
    SubmitTypeEnum type;

    /**
     * 是否为远程评测
     */
    @NotNull
    Boolean isRemote;

    /**
     * 程序
     */
    @Valid
    @NotNull
    Program program;

    /**
     * 评测样例集合
     */
    @NotNull
    @NoNullElement
    @Size(max = MAX_CASES_SIZE)
    List<JudgeCase> judgeCases;

    // TODO vj判题
    // TODO 训练、团队

}
