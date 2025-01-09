package org.ricky.core.judger.alter.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Response;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/9
 * @className JudgeResponse
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JudgeResponse implements Response {
}
