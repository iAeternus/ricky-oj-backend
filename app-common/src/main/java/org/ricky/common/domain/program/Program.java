package org.ricky.common.domain.program;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.LanguageEnum;
import org.ricky.common.domain.marker.ValueObject;

import static org.ricky.common.constants.CommonConstants.MAX_CODE_LENGTH;

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
    @Min(1)
    @NotNull
    @Max(MAX_CODE_LENGTH)
    Integer length;

    /**
     * 代码
     */
    @NotBlank
    @Size(max = MAX_CODE_LENGTH)
    String code;

    /**
     * 语言
     */
    @NotNull
    LanguageEnum language;

}
