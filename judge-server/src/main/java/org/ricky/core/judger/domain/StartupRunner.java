package org.ricky.core.judger.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ricky.core.common.properties.JudgerProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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

    @Override
    public void run(String... args) throws Exception {
        // TODO
    }
}
