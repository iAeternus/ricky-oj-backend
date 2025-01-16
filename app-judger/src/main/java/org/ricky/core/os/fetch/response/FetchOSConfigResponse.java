package org.ricky.core.os.fetch.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Response;

import java.util.Map;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/7
 * @className FetchOSConfigResponse
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FetchOSConfigResponse implements Response {

    Map<String, Object> info;

}
