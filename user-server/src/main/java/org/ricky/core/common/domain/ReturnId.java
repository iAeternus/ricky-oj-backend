package org.ricky.core.common.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.ValueObject;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/9/24
 * @className ReturnId
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReturnId implements ValueObject {

    String id;

    public static ReturnId returnId(String id) {
        return ReturnId.builder()
                .id(id)
                .build();
    }

    @Override
    public String toString() {
        return id;
    }
}
