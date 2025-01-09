package org.ricky.core.judge.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.domain.AggregateRoot;
import org.ricky.core.judge.domain.event.SubmittedEvent;
import org.ricky.core.judge.domain.result.JudgeResult;
import org.ricky.core.judge.domain.submit.Submit;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

import static org.ricky.common.constants.CommonConstants.JUDGE_COLLECTION;
import static org.ricky.common.constants.CommonConstants.JUDGE_ID_PREFIX;
import static org.ricky.common.utils.SnowflakeIdGenerator.newSnowflakeId;
import static org.ricky.core.judge.domain.JudgeStatusEnum.*;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/6
 * @className Judge
 * @desc 评测信息
 */
@Getter
@Document(JUDGE_COLLECTION)
@TypeAlias(JUDGE_COLLECTION)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Judge extends AggregateRoot {

    /**
     * 提交信息
     */
    private Submit submit;

    /**
     * 提交者IP
     */
    private String ip;

    /**
     * 提交时间
     */
    private Instant submitAt;

    /**
     * 评测状态
     */
    private JudgeStatusEnum status;

    /**
     * 评测结果
     */
    private JudgeResult result;

    public Judge(Submit submit, String ip, UserContext userContext) {
        super(newJudgeId(), userContext);
        init(submit, ip);
        addOpsLog("创建评测对象", userContext);
    }

    public static String newJudgeId() {
        return JUDGE_ID_PREFIX + newSnowflakeId();
    }

    public void onSubmit(UserContext userContext) {
        raiseEvent(new SubmittedEvent(getId(), submit.getProblemId(), submit.getCustomId(), submit.getType(), submit.getIsRemote(), userContext));
        addOpsLog("已提交，等待评测", userContext);
    }

    public void modifyStatus(JudgeStatusEnum newStatus, UserContext userContext) {
        this.status = newStatus;
        addOpsLog("修改评测状态", userContext);
    }

    public void submitFailed(UserContext userContext) {
        this.status = SUBMITTED_FAILED;
        this.result = JudgeResult.submitFailed();
        addOpsLog("提交失败", userContext);
    }

    public void judgeFailed(UserContext userContext) {
        this.status = SYSTEM_ERROR;
        this.result = JudgeResult.judgeFailed();
        addOpsLog("评测失败", userContext);
    }

    private void init(Submit submit, String ip) {
        this.submit = submit;
        this.ip = ip;
        this.submitAt = Instant.now();
        this.status = PENDING;
        this.result = null;
    }

}
