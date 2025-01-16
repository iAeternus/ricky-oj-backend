package org.ricky.apiTest.core.tag;

import io.restassured.response.Response;
import org.ricky.apiTest.BaseApiTest;
import org.ricky.core.tag.alter.command.CreateProblemTagCommand;
import org.ricky.core.tag.alter.command.UpdateTagInfoCommand;
import org.ricky.core.tag.alter.response.CreateProblemTagResponse;

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

    public static Response removeProblemTagRaw(String jwt, String tagId) {
        return BaseApiTest.given(jwt)
                .when()
                .delete(ROOT_URL + "/{tagId}/remove", tagId);
    }

    public static void removeProblemTag(String jwt, String tagId) {
        removeProblemTagRaw(jwt, tagId)
                .then()
                .statusCode(200);
    }

    public static Response updateProblemTagRaw(String jwt, String tagId, UpdateTagInfoCommand command) {
        return BaseApiTest.given(jwt)
                .body(command)
                .when()
                .put(ROOT_URL + "/{tagId}/update", tagId);
    }

    public static void updateProblemTag(String jwt, String tagId, UpdateTagInfoCommand command) {
        updateProblemTagRaw(jwt, tagId, command)
                .then()
                .statusCode(200);
    }

}
