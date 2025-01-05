package org.ricky.common.domain;

import lombok.Value;
import org.ricky.common.domain.marker.Response;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/4
 * @className LoginResponse
 * @desc
 */
@Value
public class LoginResponse implements Response {

    String userId;
    String jwt;

    public LoginResponse(String userId, String jwt) {
        this.userId = userId;
        this.jwt = jwt;
    }

}
