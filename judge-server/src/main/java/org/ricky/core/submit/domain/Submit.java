package org.ricky.core.submit.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.domain.AggregateRoot;
import org.ricky.core.submit.domain.judgecase.JudgeCase;
import org.ricky.core.submit.domain.program.Program;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

import static org.ricky.common.constants.CommonConstants.SUBMIT_COLLECTION;
import static org.ricky.common.constants.CommonConstants.SUBMIT_ID_PREFIX;
import static org.ricky.common.utils.SnowflakeIdGenerator.newSnowflakeId;
import static org.ricky.core.submit.domain.JudgeStatusEnum.SUBMITTING;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/6
 * @className Submit
 * @desc 评测信息
 */
@Getter
@Document(SUBMIT_COLLECTION)
@TypeAlias(SUBMIT_COLLECTION)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Submit extends AggregateRoot {

    /**
     * 题目ID
     */
    private String problemId;

    /**
     * 题目自定义ID，例如：ROJ-1
     */
    private String customId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 提交时间
     */
    private Instant submitAt;

    /**
     * true=全部人可见 false=仅自己可见
     */
    private boolean share;

    /**
     * 评测状态
     */
    private JudgeStatusEnum status;

    /**
     * 错误提醒（编译错误，或者vj提醒）
     */
    private String errorMessage;

    /**
     * 运行时间(ms)
     */
    private int time;

    /**
     * 运行内存(kb)
     */
    private int memory;

    /**
     * 程序
     */
    private Program program;

    /**
     * 评测机名称
     */
    private String judgementName;

    /**
     * 提交者IP
     */
    private String ip;

    /**
     * 评测样例集合
     */
    private List<JudgeCase> judgeCases;

    // TODO vj判题

    public Submit(String problemId, String customId, String nickname, boolean share, int time, int memory,
                  Program program, String judgementName, String ip, List<JudgeCase> judgeCases, UserContext userContext) {
        super(newJudgeId(), userContext);
        init(problemId, customId, nickname, share, time, memory, program, judgementName, ip, judgeCases);
        addOpsLog("创建评测对象", userContext);
    }

    public static String newJudgeId() {
        return SUBMIT_ID_PREFIX + newSnowflakeId();
    }

    private void init(String problemId, String customId, String nickname, boolean share, int time, int memory,
                      Program program, String judgementName, String ip, List<JudgeCase> judgeCases) {
        this.problemId = problemId;
        this.customId = customId;
        this.nickname = nickname;
        this.submitAt = Instant.now();
        this.share = share;
        this.status = SUBMITTING;
        this.errorMessage = null;
        this.time = time;
        this.memory = memory;
        this.program = program;
        this.judgementName = judgementName;
        this.ip = ip;
        this.judgeCases = judgeCases;
    }

}
