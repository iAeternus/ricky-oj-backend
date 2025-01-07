package org.ricky.apiTest.core.judger;

import org.ricky.apiTest.BaseApiTest;
import org.ricky.core.judger.fetch.dto.response.FetchJudgerInfoResponse;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/7
 * @className JudgerApi
 * @desc
 */
public class JudgerApi {

    private static final String ROOT_URL = "/judger";

    public static FetchJudgerInfoResponse fetchJudgerInfo(String jwt) {
        return BaseApiTest.given(jwt)
                .when()
                .get(ROOT_URL + "/version")
                .then()
                .statusCode(200)
                .extract()
                .as(FetchJudgerInfoResponse.class);
    }

}
