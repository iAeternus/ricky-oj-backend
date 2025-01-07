package org.ricky.core.common.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.ricky.common.validation.ip.IP;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.ricky.common.utils.IPUtils.getLocalIpv4Address;
import static org.ricky.common.utils.IPUtils.getServiceIp;

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
     * 评测并发最大数
     * -1表示最大并行任务数为cpu核心数+1
     */
    @NotNull
    private Integer maxTaskCount;

    /**
     * localhost表示使用默认本地ipv4，若是部署其它服务器，务必使用公网ip
     */
    @IP
    @NotBlank
    private String ip;

    /**
     * 端口号
     */
    @NotNull
    private Integer port;

    /**
     * 评测机名称
     * 唯一不可重复
     */
    @NotBlank
    private String name;

    public int correctMaxTaskCount() {
        if (maxTaskCount == -1) {
            return CPU_COUNT + 1;
        }
        return maxTaskCount;
    }

    public String correctIpv4() {
        if ("localhost".equalsIgnoreCase(ip)) {
            return getLocalIpv4Address();
        }
        return ip;
    }

    public String correctServiceIp() {
        if ("localhost".equalsIgnoreCase(ip)) {
            return getServiceIp();
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
        @NotNull
        private Boolean enabled;

        /**
         * 远程评测并发最大数
         * -1表示最大并行任务数为cpu核心数*2+1
         */
        @NotNull
        private Integer maxRemoteTaskCount;

        public int correctMaxRemoteTaskCount() {
            if(maxRemoteTaskCount == -1) {
                return CPU_COUNT * 2 + 1;
            }
            return maxRemoteTaskCount;
        }

    }

}
