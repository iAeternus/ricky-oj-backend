package org.ricky.core.oss.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.ValueObject;

import java.time.Instant;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/15
 * @className OssToken
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OssToken implements ValueObject {

    String securityToken;
    String accessKeySecret;
    String accessKeyId;
    String bucket;
    String endpoint;
    boolean secure;
    Instant expiration;
    String folder;

}
