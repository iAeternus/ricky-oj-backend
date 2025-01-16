package org.ricky.core.judger.domain.loader;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.ricky.common.utils.MyObjectMapper;
import org.ricky.common.properties.LanguageProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.ricky.common.utils.ValidationUtils.nonNull;
import static org.ricky.common.properties.LanguageProperties.parseMemoryStr;
import static org.ricky.common.properties.LanguageProperties.parseTimeStr;
import static org.ricky.core.common.utils.YamlUtils.loadYaml;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/13
 * @className LanguageConfigLoader
 * @desc
 */
@Component
public class LanguageConfigLoader {

    private static final String FILENAME = "language.yml";

    private static final List<String> DEFAULT_ENV = Arrays.asList(
            "PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin",
            "LANG=en_US.UTF-8",
            "LC_ALL=en_US.UTF-8",
            "LANGUAGE=en_US:en",
            "HOME=/w");

    private static final List<String> PYTHON3_ENV = Arrays.asList("LANG=en_US.UTF-8",
            "LANGUAGE=en_US:en", "LC_ALL=en_US.UTF-8", "PYTHONIOENCODING=utf-8");

    private static final List<String> GOLANG_COMPILE_ENV = Arrays.asList(
            "GOCACHE=/w", "GOPATH=/w/go", "PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin",
            "LANG=en_US.UTF-8", "LANGUAGE=en_US:en", "LC_ALL=en_US.UTF-8");

    private static final List<String> GOLANG_RUN_ENV = Arrays.asList(
            "GOCACHE=off", "GODEBUG=madvdontneed=1",
            "PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin",
            "LANG=en_US.UTF-8", "LANGUAGE=en_US:en", "LC_ALL=en_US.UTF-8");

    private static final AtomicBoolean INIT = new AtomicBoolean(false);

    /**
     * 键=langName 值=语言配置
     */
    private static Map<String, LanguageProperties> languagePropertiesMap;

    @PostConstruct
    public void init() {
        if (INIT.compareAndSet(false, true)) {
            Iterable<Object> languageProperties = loadYaml(FILENAME);
            languagePropertiesMap = new HashMap<>();
            languageProperties.forEach(configObj -> {
                JSONObject configJson = JSONUtil.parseObj(configObj);
                LanguageProperties languageConfig = buildLanguageConfig(configJson);
                languagePropertiesMap.put(languageConfig.getLanguage(), languageConfig);
            });
        }
    }

    /**
     * 根据语言名称获取语言配置
     *
     * @param langName 语言名称
     * @return 语言配置
     */
    public LanguageProperties getLanguageConfigByName(String langName) {
        return languagePropertiesMap.get(langName);
    }

    private LanguageProperties buildLanguageConfig(JSONObject configJson) {
        LanguageProperties languageConfig = new LanguageProperties();
        languageConfig.setLanguage(configJson.getStr("language"));
        languageConfig.setSrcName(configJson.getStr("src_path"));
        languageConfig.setExeName(configJson.getStr("exe_path"));

        JSONObject compileJson = configJson.getJSONObject("compile");
        if (nonNull(compileJson)) {
            languageConfig.setCompileCommand(compileJson.getStr("command"));
            languageConfig.setCompileEnvs(chooseCompileEnv(compileJson.getStr("env")));
            languageConfig.setMaxCpuTime(parseTimeStr(compileJson.getStr("maxCpuTime")));
            languageConfig.setMaxRealTime(parseTimeStr(compileJson.getStr("maxRealTime")));
            languageConfig.setMaxMemory(parseMemoryStr(compileJson.getStr("maxMemory")));
        }

        JSONObject runJson = configJson.getJSONObject("run");
        if (nonNull(runJson)) {
            languageConfig.setRunCommand(runJson.getStr("command"));
            languageConfig.setRunEnvs(chooseRunEnv(runJson.getStr("env")));
        }
        return languageConfig;
    }

    private List<String> chooseCompileEnv(String env) {
        env = env.toLowerCase();
        return switch (env) {
            case "python3" -> PYTHON3_ENV;
            case "golang_compile" -> GOLANG_COMPILE_ENV;
            default -> DEFAULT_ENV;
        };
    }

    private List<String> chooseRunEnv(String env) {
        env = env.toLowerCase();
        return switch (env) {
            case "python3" -> PYTHON3_ENV;
            case "golang_compile" -> GOLANG_RUN_ENV;
            default -> DEFAULT_ENV;
        };
    }
}
