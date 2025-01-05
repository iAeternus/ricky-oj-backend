package org.ricky.core.problem.domain.setting.limit;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.ValueObject;
import org.ricky.common.validation.number.FloatNumber;

import static java.lang.Double.compare;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/26
 * @className Limit
 * @desc 限制
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Limit implements ValueObject {

    /**
     * 时间限制 ms
     */
    @FloatNumber(min = 0)
    double timeLimit;

    /**
     * 内存空间限制 MB
     */
    @FloatNumber(min = 0)
    double memoryLimit;

    /**
     * 栈空间限制 MB
     */
    @FloatNumber(min = 0, max = 8)
    double stackLimit;

    public boolean occursTimeLimitExceeded(double actualTime) {
        return compare(actualTime, timeLimit) > 0;
    }

    public boolean occursMemoryLimitExceeded(double actualMemory) {
        return compare(actualMemory, memoryLimit) > 0;
    }

    public boolean occursStackOverflow(double actualStackMemory) {
        return compare(actualStackMemory, stackLimit) > 0;
    }

    public static Limit defaultLimit() {
        return Limit.builder()
                .timeLimit(400)
                .memoryLimit(1)
                .stackLimit(1)
                .build();
    }

}
