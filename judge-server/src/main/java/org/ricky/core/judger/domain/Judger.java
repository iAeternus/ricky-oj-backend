package org.ricky.core.judger.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.domain.AggregateRoot;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;

import static org.ricky.common.constants.CommonConstants.JUDGER_COLLECTION;
import static org.ricky.common.constants.CommonConstants.JUDGER_ID_PREFIX;
import static org.ricky.common.utils.SnowflakeIdGenerator.newSnowflakeId;
import static org.ricky.core.common.properties.JudgerProperties.CPU_COUNT;
import static org.ricky.core.judger.domain.JudgerStatusEnum.DISABLE;

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
    private int port;

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

    public Judger(String name, String ip, int port, int cpuCore, int maxTaskCount, boolean isRemote, UserContext userContext) {
        super(newJudgerId(), userContext);
        init(name, ip, port, cpuCore, maxTaskCount, isRemote);
        addOpsLog("创建评测机", userContext);
    }

    public static String newJudgerId() {
        return JUDGER_ID_PREFIX + newSnowflakeId();
    }

    public String getUrl() {
        return ip + ":" + port;
    }

    public void refresh(String name, String ip, int port, int cpuCore, int maxTaskCount, boolean isRemote, UserContext userContext) {
        init(name, ip, port, cpuCore, maxTaskCount, isRemote);
        addOpsLog("刷新评测机", userContext);
    }

    private void init(String name, String ip, int port, int cpuCore, int maxTaskCount, boolean isRemote) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.cpuCore = cpuCore;
        this.taskCount = 0;
        this.maxTaskCount = maxTaskCount;
        this.status = DISABLE;
        this.isRemote = isRemote;
    }

}
