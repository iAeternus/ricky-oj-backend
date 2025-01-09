package org.ricky.core.problem.domain.answer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.ValueObject;
import org.ricky.common.domain.program.Program;

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
     * 是否启用
     */
    Boolean enable;

    /**
     * 程序
     */
    Program program;

}
