package org.ricky.core.user.domain.title;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.ValueObject;
import org.ricky.common.validation.color.Color;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/27
 * @className Title
 * @desc 头衔、称号
 * 头衔可以编辑，但是颜色系统指定
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Title implements ValueObject {

    /**
     * 头衔、称号
     */
    String name;

    /**
     * 头衔、称号的颜色
     */
    @Color
    String color;

}
