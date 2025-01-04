package org.ricky.core.problem.domain.setting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.ricky.common.domain.UploadedFile;
import org.ricky.core.problem.domain.LanguageEnum;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/27
 * @className SPJSetting
 * @desc 特判程序或交互程序的设置
 */
@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SPJSetting {

    /**
     * 特判程序或交互程序的代码
     */
    private String spjCode;

    /**
     * 特判程序或交互程序的语言
     */
    private LanguageEnum spjLanguage;

    /**
     * 特判程序或交互程序的额外文件
     */
    @Setter
    private UploadedFile userExtraFile;

    /**
     * 特判程序或交互程序的额外文件
     */
    @Setter
    private UploadedFile judgeExtraFile;

    public static SPJSetting defaultSPJSetting() {
        return SPJSetting.builder()
                .spjCode(null)
                .spjLanguage(null)
                .userExtraFile(null)
                .judgeExtraFile(null)
                .build();
    }

    public void setSpj(String spjCode, LanguageEnum spjLanguage) {
        this.spjCode = spjCode;
        this.spjLanguage = spjLanguage;
    }

}
