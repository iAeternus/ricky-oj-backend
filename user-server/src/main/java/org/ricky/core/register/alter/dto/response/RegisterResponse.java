package org.ricky.core.register.alter.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Response;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/29
 * @className RegisterResponse
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterResponse implements Response {
}
