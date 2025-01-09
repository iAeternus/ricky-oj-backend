package org.ricky.core.judger.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ricky.core.common.properties.JudgerProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.ricky.common.context.UserContext.ADMIN_USER;
import static org.ricky.common.utils.ValidationUtils.isNull;
import static org.ricky.core.common.properties.JudgerProperties.CPU_COUNT;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/6
 * @className StartupRunner
 * @desc 启动后处理
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StartupRunner implements CommandLineRunner {

    private final JudgerProperties judgerProperties;
    private final JudgerProperties.RemoteJudgeProperties remoteJudgeProperties;
    private final JudgerRepository judgerRepository;
    private final JudgerFactory judgerFactory;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("IP of the current judge server: {}", judgerProperties.getIp());
        log.info("Port of the current judge server: {}", judgerProperties.getPort());

        String name = judgerProperties.getName();
        int maxTaskCount = judgerProperties.correctMaxTaskCount();
        String ipv4 = judgerProperties.correctIpv4();
        Integer port = judgerProperties.getPort();
        Judger prototype = judgerFactory.createLocalJudger(name, ipv4, port, CPU_COUNT, maxTaskCount, ADMIN_USER);

        // 刷新评测机
        Judger judger = judgerRepository.byUrl(ipv4, judgerProperties.getPort());
        if(isNull(judger)) {
            judger = judgerFactory.createByPrototype(prototype, ADMIN_USER);
        }
        judgerRepository.save(judgerFactory.updateByPrototype(judger, prototype, ADMIN_USER));

        // 初始化远程评测机
        if(remoteJudgeProperties.getEnabled()) {
            int maxRemoteTaskCount = remoteJudgeProperties.correctMaxRemoteTaskCount();
            Judger remoteJudger = judgerFactory.createRemoteJudger(name, ipv4, port, CPU_COUNT, maxRemoteTaskCount, ADMIN_USER);
            judgerRepository.save(remoteJudger);
        }

        log.info("ROJ-JudgeServer had successfully started! The judge config and sandbox config Info: {}", fetchJudgerInfo());
    }

    public Map<String, Object> fetchJudgerInfo() {
        Map<String, Object> info = new HashMap<>();

        info.put("version", "1.0.0");
        info.put("currentTime", LocalDateTime.now());
        info.put("judgerName", judgerProperties.getName());
        info.put("cpu", CPU_COUNT);
        info.put("languages", List.of("G++ 9.4.0", "GCC 9.4.0", "Python 3.7.5",
                "Python 2.7.17", "OpenJDK 1.8", "Golang 1.19", "C# Mono 4.6.2",
                "PHP 7.2.24", "JavaScript Node 14.19.0", "JavaScript V8 8.4.109",
                "PyPy 2.7.18 (7.3.9)", "PyPy 3.9.17 (7.3.12)", "Ruby 2.5.1", "Rust 1.65.0")); // TODO update
        info.put("maxTaskCount", judgerProperties.correctMaxTaskCount());
        if (remoteJudgeProperties.getEnabled()) {
            info.put("isRemote", true);
            info.put("remoteJudgeMaxTaskCount", remoteJudgeProperties.correctMaxRemoteTaskCount());
        }

        // TODO
        // String versionResp = "";
        // try {
        //     versionResp = Sandbox.getRestTemplate().getForObject(Sandbox.getSandboxBaseUrl() + "/version", String.class);
        // } catch (Exception e) {
        //     info.put("SandBoxMsg", MapUtil.builder().put("error", e.getMessage()).map());
        //     return info;
        // }
        //
        // info.put("SandBoxMsg", JSONUtil.parseObj(versionResp));

        return info;
    }

}
