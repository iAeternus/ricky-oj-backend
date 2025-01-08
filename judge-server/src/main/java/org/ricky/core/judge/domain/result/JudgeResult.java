package org.ricky.core.judge.domain.result;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.ValueObject;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/7
 * @className JudgeResult
 * @desc 评测结果
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JudgeResult implements ValueObject {

    /**
     * 错误提醒（编译错误，或者vj提醒）
     */
    String errorMessage;

    /**
     * 运行时间(ms)
     */
    Integer time;

    /**
     * 运行内存(kb)
     */
    Integer memory;

    public static JudgeResult submitFailed() {
        return JudgeResult.builder()
                .errorMessage("Domain event handling error. Please try to submit again!")
                .time(Integer.MAX_VALUE)
                .memory(Integer.MAX_VALUE)
                .build();
    }

    public static JudgeResult judgeFailed() {
        return JudgeResult.builder()
                .errorMessage("The something has gone wrong with the judge server. Please report this to administrator.")
                .time(Integer.MAX_VALUE)
                .memory(Integer.MAX_VALUE)
                .build();
    }
}
