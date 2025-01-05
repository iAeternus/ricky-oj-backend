package org.ricky.core.tag.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.ValueObject;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/5
 * @className CachedTag
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CachedTag implements ValueObject {

    String name;
    String color;
    String oj;
    String groupId;

}
