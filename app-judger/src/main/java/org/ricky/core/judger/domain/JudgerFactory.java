package org.ricky.core.judger.domain;

import org.ricky.common.context.UserContext;
import org.springframework.stereotype.Component;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/7
 * @className JudgerFactory
 * @desc
 */
@Component
public class JudgerFactory {

    public Judger createLocalJudger(String name, String ip, int port, int cpuCore, int maxTaskCount, UserContext userContext) {
        return new Judger(name, ip, port, cpuCore, maxTaskCount, false, userContext);
    }

    public Judger createRemoteJudger(String name, String ip, int port, int cpuCore, int maxTaskCount, UserContext userContext) {
        return new Judger(name, ip, port, cpuCore, maxTaskCount, true, userContext);
    }

    public Judger createByPrototype(Judger prototype, UserContext userContext) {
        return new Judger(prototype.getName(), prototype.getIp(), prototype.getPort(), prototype.getCpuCore(),
                prototype.getMaxTaskCount(), prototype.isRemote(), userContext);
    }

    public Judger updateByPrototype(Judger origin, Judger prototype, UserContext userContext) {
        origin.refresh(prototype.getName(), prototype.getIp(), prototype.getPort(), prototype.getCpuCore(),
                prototype.getMaxTaskCount(), prototype.isRemote(), userContext);
        return origin;
    }

}
