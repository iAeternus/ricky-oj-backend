package org.ricky.core.judger.domain;

import lombok.RequiredArgsConstructor;
import org.ricky.common.properties.LanguageProperties;
import org.ricky.core.judger.domain.context.JudgeResult;
import org.ricky.core.judger.domain.loader.LanguageConfigLoader;
import org.ricky.dto.alter.response.Judge;
import org.ricky.dto.fetch.response.ProblemSetting;
import org.springframework.stereotype.Service;

import static java.lang.Math.min;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/13
 * @className JudgerDomainService
 * @desc
 */
@Service
@RequiredArgsConstructor
public class JudgerDomainService {

    private final LanguageConfigLoader languageConfigLoader;
    private final JudgerHandler judgerHandler;

    public JudgeResult judge(ProblemSetting problemSetting, Judge judge) {
        LanguageProperties languageConfig = languageConfigLoader.getLanguageConfigByName(judge.getLangName());
        problemSetting.correctLimit(languageConfig);

        JudgeResult result = judgerHandler.judge(problemSetting, judge);
        return JudgeResult.builder()
                .judgeId(result.getJudgeId())
                .status(result.getStatus())
                .errorMessage(result.getErrorMessageOrDefault(""))
                .memory(min(result.getMemory(), problemSetting.getMemoryLimit() * 1024))
                .time(min(result.getTime(), problemSetting.getTimeLimit()))
                .build();
    }

}
