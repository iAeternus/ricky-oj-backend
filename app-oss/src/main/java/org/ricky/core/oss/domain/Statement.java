package org.ricky.core.oss.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.ValueObject;

import java.util.List;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/15
 * @className Statement
 * @desc 策略语句
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Statement implements ValueObject {

    /**
     * 策略语句的效果
     */
    String Effect;

    /**
     * 允许或拒绝的操作
     */
    String Action;

    /**
     * 操作适用的资源路径
     */
    List<String> Resource;

}
