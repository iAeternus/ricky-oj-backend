package org.ricky.core.submit.domain;

import lombok.Getter;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/6
 * @className JudgeStatusEnum
 * @desc 评测状态
 */
@Getter
public enum JudgeStatusEnum {

    NOT_SUBMITTED(-10, "Not Submitted", null),
    SUBMITTED_UNKNOWN_RESULT(-5, "Submitted Unknown Result", null),
    CANCELED(-4, "Canceled", "ca"),
    PRESENTATION_ERROR(-3, "Presentation Error", "pe"),
    COMPILE_ERROR(-2, "Compile Error", "ce"),
    WRONG_ANSWER(-1, "Wrong Answer", "wa"),
    ACCEPTED(0, "Accepted", "ac"),
    TIME_LIMIT_EXCEEDED(1, "Time Limit Exceeded", "tle"),
    MEMORY_LIMIT_EXCEEDED(2, "Memory Limit Exceeded", "mle"),
    RUNTIME_ERROR(3, "Runtime Error", "re"),
    SYSTEM_ERROR(4, "System Error", "se"),
    PENDING(5, "Pending", null),
    COMPILING(6, "Compiling", null),
    JUDGING(7, "Judging", null),
    PARTIAL_ACCEPTED(8, "Partial Accepted", "pa"),
    SUBMITTING(9, "Submitting", null),
    SUBMITTED_FAILED(10, "Submitted Failed", null),
    NULL(15, "No Status", null),
    JUDGE_SERVER_SUBMIT_PREFIX(-1002, "Submit SubmitId-ServerId:", null);

    final Integer status;
    final String name;
    final String columnName;

    JudgeStatusEnum(Integer status, String name, String columnName) {
        this.status = status;
        this.name = name;
        this.columnName = columnName;
    }

}
