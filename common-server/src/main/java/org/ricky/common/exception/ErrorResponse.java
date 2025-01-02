package org.ricky.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Response;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/9/25
 * @className ErrorResponse
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse implements Response {

    MyError error;

}
