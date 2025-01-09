package org.ricky.core.common.properties;

import lombok.Data;
import org.ricky.common.validation.ip.IP;
import org.ricky.core.common.utils.IPUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/6
 * @className JudgerProperties
 * @desc
 */
@Data
@Component
@Validated
@ConfigurationProperties("my.roj-judger-server")
public class JudgerProperties {

    /**
     * CPU数量
     */
    public static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * 是否开启评测机
     */
    private Boolean enabled;

    /**
     * 评测并发最大数
     * -1或不配置表示最大并行任务数为cpu核心数+1
     */
    private Integer maxTaskCount = CPU_COUNT + 1;

    /**
     * localhost表示使用默认本地ipv4，若是部署其它服务器，务必使用公网ip
     */
    @IP
    private String ip = "localhost";

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 评测机名称
     * 唯一不可重复
     */
    private String name;

    public int correctMaxTaskCount() {
        if (maxTaskCount == -1) {
            return CPU_COUNT + 1;
        }
        return maxTaskCount;
    }

    public String correctIpv4() {
        if ("localhost".equalsIgnoreCase(ip)) {
            return IPUtils.getLocalIpv4Address();
        }
        return ip;
    }

    public String correctServiceIp() {
        if ("localhost".equalsIgnoreCase(ip)) {
            return IPUtils.getServiceIp();
        }
        return ip;
    }

    @Data
    @Component
    @Validated
    @ConfigurationProperties("my.roj-judger-server.remote-judge")
    public static class RemoteJudgeProperties {

        /**
         * 当前判题服务器是否开启远程虚拟判题功能
         */
        private Boolean enabled = false;

        /**
         * 远程评测并发最大数
         * -1或不配置表示最大并行任务数为cpu核心数*2+1
         */
        private Integer maxRemoteTaskCount = CPU_COUNT * 2 + 1;

        public int correctMaxRemoteTaskCount() {
            if (maxRemoteTaskCount == -1) {
                return CPU_COUNT * 2 + 1;
            }
            return maxRemoteTaskCount;
        }

    }

}
