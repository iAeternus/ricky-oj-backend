package org.ricky.core.problem.domain.setting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.ricky.common.domain.marker.ValueObject;
import org.ricky.common.exception.MyException;

import static org.ricky.common.exception.ErrorCodeEnum.*;
import static org.ricky.common.utils.CollectionUtils.mapOf;
import static org.ricky.common.utils.ValidationUtils.isBlank;
import static org.ricky.common.utils.ValidationUtils.isNotBlank;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/27
 * @className VJSetting
 * @desc 远程评测设置
 */
@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VJSetting implements ValueObject {

    @Schema(name = "是否为vj判题")
    private boolean isRemote;

    @Schema(name = "题目来源（例如vj判题时HDU-1000的链接）")
    private String source;

    public static VJSetting defaultVJSetting() {
        return VJSetting.builder()
                .isRemote(false)
                .build();
    }

    public void setSource(String source) {
        this.isRemote = true;
        this.source = source;
    }

    @Override
    public void validate() {
        if (isRemote && isBlank(source)) {
            throw new MyException(LACK_OF_PROBLEM_SOURCE, "Lack of problem source");
        }
        if (!isRemote && isNotBlank(source)) {
            throw new MyException(DO_NOT_SUPPORT_REMOTE, "Problem does not support remote judgement.",
                    mapOf("wrongSource", source));
        }
    }

    @Override
    public void correct() {
        if (!isRemote) {
            source = null;
        }
    }
}
