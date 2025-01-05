package org.ricky.apiTest.core.tag;

import io.restassured.response.Response;
import org.ricky.apiTest.BaseApiTest;
import org.ricky.core.tag.alter.dto.command.CreateProblemTagCommand;
import org.ricky.core.tag.alter.dto.response.CreateProblemTagResponse;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/5
 * @className TagApi
 * @desc
 */
public class TagApi {

    private static final String ROOT_URL = "/tag";

    public static Response createProblemTagRaw(String jwt, CreateProblemTagCommand command) {
        return BaseApiTest.given(jwt)
                .body(command)
                .when()
                .post(ROOT_URL + "/create");
    }

    public static CreateProblemTagResponse createProblemTag(String jwt, CreateProblemTagCommand command) {
        return createProblemTagRaw(jwt, command)
                .then()
                .statusCode(201)
                .extract()
                .as(CreateProblemTagResponse.class);
    }

}
