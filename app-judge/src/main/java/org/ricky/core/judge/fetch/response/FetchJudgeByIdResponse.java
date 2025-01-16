package org.ricky.core.judge.fetch.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Response;
import org.ricky.common.domain.program.Program;
import org.ricky.core.judge.domain.JudgeStatusEnum;
import org.ricky.core.judge.domain.submit.SubmitTypeEnum;
import org.ricky.core.judge.domain.submit.judgecase.JudgeCase;

import java.time.Instant;
import java.util.List;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/9
 * @className Judge
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FetchJudgeByIdResponse implements Response {

    @Schema(name = "题目ID")
    String problemId;

    @Schema(name = "题目自定义ID，例如：ROJ-1")
    String customId;

    @Schema(name = "用户昵称")
    String nickname;

    @Schema(name = "true=全部人可见 false=仅自己可见")
    Boolean share;

    @Schema(name = "提交类型")
    SubmitTypeEnum type;

    @Schema(name = "是否为远程评测")
    Boolean isRemote;

    @Schema(name = "程序")
    Program program;

    @Schema(name = "评测样例集合")
    List<JudgeCase> judgeCases;

    @Schema(name = "提交者IP")
    String ip;

    @Schema(name = "提交时间")
    Instant submitAt;

    @Schema(name = "评测状态")
    JudgeStatusEnum status;

    @Schema(name = "错误提醒（编译错误，或者vj提醒）")
    String errorMessage;

    @Schema(name = "运行时间(ms)")
    Integer time;

    @Schema(name = "运行内存(kb)")
    Integer memory;

    @Schema(name = "修改时间")
    Instant updatedAt;

}
