package org.ricky.core.submit.domain.program;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.LanguageEnum;
import org.ricky.common.domain.marker.ValueObject;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/6
 * @className Program
 * @desc 程序
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Program implements ValueObject {

    /**
     * 代码长度
     */
    int length;

    /**
     * 代码
     */
    String code;

    /**
     * 语言
     */
    LanguageEnum language;

}
