package org.ricky.core.judger.domain.context;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.ValueObject;
import org.ricky.dto.alter.JudgeStatusEnum;

import static org.ricky.common.utils.ValidationUtils.isNotBlank;
import static org.ricky.dto.alter.JudgeStatusEnum.*;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/10
 * @className JudgeResult
 * @desc 评测机评测结果
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JudgeResult implements ValueObject {

    String judgeId;
    JudgeStatusEnum status;
    String errorMessage;
    Integer memory;
    Integer time;
    // TODO oi score

    public boolean hasError() {
        return status == COMPILE_ERROR ||
                status == SYSTEM_ERROR ||
                status == RUNTIME_ERROR ||
                status == SUBMITTED_FAILED;
    }

    /**
     * 只有 发生错误 且 错误信息不为空 时才获取错误信息，否则只能获取默认信息
     */
    public String getErrorMessageOrDefault(String defaultValue) {
        return hasError() && isNotBlank(errorMessage) ? errorMessage : defaultValue;
    }

}
