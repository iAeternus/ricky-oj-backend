package org.ricky.core.verification.fetch.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Response;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/30
 * @className FetchVerificationByIdResponse
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FetchVerificationByIdResponse implements Response {

    String code;

}
