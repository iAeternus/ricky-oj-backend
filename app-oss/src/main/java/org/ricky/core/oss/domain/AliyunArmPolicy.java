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
 * @className AliyunArmPolicy
 * @desc 阿里云 RAM（Resource Access Management）策略
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AliyunArmPolicy implements ValueObject {

    /**
     * 策略版本
     */
    String Version;

    /**
     * 策略语句集合
     */
    List<Statement> Statement;

}
