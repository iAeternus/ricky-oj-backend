package org.ricky.core.judge.alter.command;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Command;
import org.ricky.common.validation.id.Id;
import org.ricky.common.domain.program.Program;

import static org.ricky.common.constants.CommonConstants.PROBLEM_ID_PREFIX;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/6
 * @className SubmitCommand
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SubmitCommand implements Command {

    /**
     * 调用评测服务的token
     */
    @NotBlank
    String token;

    /**
     * 题目ID
     */
    @NotBlank
    @Id(prefix = PROBLEM_ID_PREFIX)
    String problemId;

    /**
     * true=全部人可见 false=仅自己可见
     */
    @NotNull
    Boolean share;

    /**
     * 提交类型 0=比赛提交 1=普通提交 2=自测提交
     */
    @NotNull
    Short submitType;

    /**
     * 是否为远程评测
     */
    Boolean isRemote;

    /**
     * 程序
     */
    @Valid
    @NotNull
    Program program;

}
