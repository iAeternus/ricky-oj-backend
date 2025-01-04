package org.ricky.core.problem.domain.answer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.ValueObject;
import org.ricky.core.problem.domain.LanguageEnum;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/2
 * @className Answer
 * @desc 答案
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Answer implements ValueObject {

    /**
     * 语言
     */
    LanguageEnum language;

    /**
     * 代码
     */
    String code;

    /**
     * 是否启用
     */
    boolean enable;

}
