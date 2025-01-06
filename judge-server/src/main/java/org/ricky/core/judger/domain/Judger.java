package org.ricky.core.judger.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ricky.common.domain.AggregateRoot;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import static org.ricky.common.constants.CommonConstants.JUDGER_COLLECTION;
import static org.ricky.common.constants.CommonConstants.JUDGER_ID_PREFIX;
import static org.ricky.common.utils.SnowflakeIdGenerator.newSnowflakeId;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/6
 * @className Judger
 * @desc 评测服务信息
 */
@Getter
@Document(JUDGER_COLLECTION)
@TypeAlias(JUDGER_COLLECTION)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Judger extends AggregateRoot {

    /**
     * 评测服务名称
     */
    private String name;

    /**
     * 评测机IP
     */
    private String ip;

    /**
     * 评测机端口号
     */
    private String port;

    /**
     * 评测机所在服务器cpu核心数
     */
    private int cpuCore;

    /**
     * 当前评测数
     */
    private int taskCount;

    /**
     * 评测并发最大数
     */
    private int maxTaskCount;

    /**
     * 评测机状态
     */
    private JudgerStatusEnum status;

    /**
     * 是否为远程评测vj
     */
    private boolean isRemote;

    public static String newJudgerId() {
        return JUDGER_ID_PREFIX + newSnowflakeId();
    }

}
