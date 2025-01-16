package org.ricky.common.properties;

import lombok.Data;

import java.util.List;

import static java.lang.Long.parseLong;
import static org.ricky.common.utils.ValidationUtils.isBlank;
import static org.ricky.common.utils.ValidationUtils.requireNonBlank;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/13
 * @className LanguageProperties
 * @desc
 */
@Data
public class LanguageProperties {

    /**
     * 语言名称
     */
    private String language;

    /**
     * 源代码文件名称
     */
    private String srcName;

    /**
     * 源代码的可执行文件名称
     */
    private String exeName;

    /**
     * 编译最大cpu运行时间，单位：ms，没加单位默认按ms算
     */
    private Long maxCpuTime;

    /**
     * 编译最大真实运行时间，单位：ms，没加单位默认按ms算
     */
    private Long maxRealTime;

    /**
     * 编译最大运行空间，单位：byte，没加单位默认按byte算
     */
    private Long maxMemory;

    /**
     * 编译命令
     */
    private String compileCommand;

    /**
     * 运行命令
     */
    private String runCommand;

    /**
     * 编译运行环境
     */
    private List<String> compileEnvs;

    /**
     * 执行程序环境
     */
    private List<String> runEnvs;

    public void setCompileCommand(String compileCommand) {
        requireNonBlank(srcName, "Src Name must not be null.");
        requireNonBlank(exeName, "Exe Name must not be null");

        this.compileCommand = compileCommand.replace("{src_path}", srcName).replace("{exe_path}", exeName);
    }

    public void setRunCommand(String runCommand) {
        requireNonBlank(exeName, "Exe Name must not be null");

        this.runCommand = runCommand.replace("{exe_path}", exeName);
    }

    public static long parseTimeStr(String timeStr) {
        if (isBlank(timeStr)) {
            return 3000L;
        }
        timeStr = timeStr.toLowerCase();
        if (timeStr.endsWith("s")) {
            return parseLong(timeStr.replace("s", "")) * 1000L;
        } else if (timeStr.endsWith("ms")) {
            return parseLong(timeStr.replace("ms", ""));
        } else {
            return parseLong(timeStr);
        }
    }

    public static long parseMemoryStr(String memoryStr) {
        if (isBlank(memoryStr)) {
            return 256 * 1024L * 1024L;
        }
        memoryStr = memoryStr.toLowerCase();
        if (memoryStr.endsWith("mb")) {
            return parseLong(memoryStr.replace("mb", "")) * 1024L * 1024L;
        } else if (memoryStr.endsWith("kb")) {
            return parseLong(memoryStr.replace("kb", "")) * 1024L;
        } else if (memoryStr.endsWith("b")) {
            return parseLong(memoryStr.replace("b", ""));
        } else {
            return parseLong(memoryStr);
        }
    }

}
