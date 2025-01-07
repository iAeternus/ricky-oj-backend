package org.ricky.apiTest.core.os;

import org.ricky.apiTest.BaseApiTest;
import org.ricky.core.os.fetch.response.FetchOSConfigResponse;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/7
 * @className OSApi
 * @desc
 */
public class OSApi {

    private static final String ROOT_URL = "/os";

    public static FetchOSConfigResponse fetchOSConfig(String jwt) {
        return BaseApiTest.given(jwt)
                .when()
                .get(ROOT_URL + "/config")
                .then()
                .statusCode(200)
                .extract()
                .as(FetchOSConfigResponse.class);
    }

}
