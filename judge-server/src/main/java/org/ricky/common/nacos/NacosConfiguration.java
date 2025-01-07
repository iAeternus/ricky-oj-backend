package org.ricky.common.nacos;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import lombok.RequiredArgsConstructor;
import org.ricky.core.common.properties.JudgerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;

import static org.ricky.common.utils.IPUtils.getServiceIp;
import static org.ricky.core.common.properties.JudgerProperties.CPU_COUNT;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/6
 * @className NacosConfiguration
 * @desc
 */
@Configuration
@RequiredArgsConstructor
public class NacosConfiguration {

    private final JudgerProperties judgerProperties;
    private final JudgerProperties.RemoteJudgeProperties remoteJudgeProperties;

    @Bean
    @Primary
    public NacosDiscoveryProperties nacosProperties() {
        NacosDiscoveryProperties nacosDiscoveryProperties = new NacosDiscoveryProperties();
        nacosDiscoveryProperties.setIp(judgerProperties.correctServiceIp());
        nacosDiscoveryProperties.setMetadata(getMetaData());
        nacosDiscoveryProperties.setPort(judgerProperties.getPort());
        nacosDiscoveryProperties.setService("roj-judge-server");
        return nacosDiscoveryProperties;
    }

    private Map<String, String> getMetaData() {
        Map<String, String> meta = new HashMap<>();
        int max = CPU_COUNT * 2 + 1;
        if (judgerProperties.getMaxTaskCount() != -1) {
            max = judgerProperties.getMaxTaskCount();
        }
        meta.put("maxTaskNum", String.valueOf(max));
        if (remoteJudgeProperties.getEnabled()) {
            max = (CPU_COUNT * 2 + 1) * 2;
            if (remoteJudgeProperties.getMaxRemoteTaskCount() != -1) {
                max = remoteJudgeProperties.getMaxRemoteTaskCount();
            }
            meta.put("maxRemoteTaskNum", String.valueOf(max));
        }
        meta.put("name", judgerProperties.getName()); // TODO judgeName
        return meta;
    }

}
