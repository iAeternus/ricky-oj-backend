package org.ricky.apiTest.core.problem;

import io.restassured.response.Response;
import org.ricky.apiTest.BaseApiTest;
import org.ricky.core.problem.alter.dto.command.CreateProblemCommand;
import org.ricky.core.problem.alter.dto.command.UpdateProblemSettingCommand;
import org.ricky.core.problem.alter.dto.command.UpdateProblemTagsCommand;
import org.ricky.core.problem.alter.dto.response.CreateProblemResponse;
import org.ricky.core.problem.alter.dto.response.UpdateProblemResponse;
import org.ricky.core.problem.alter.dto.response.UpdateProblemTagsResponse;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/4
 * @className ProblemApi
 * @desc
 */
public class ProblemApi {

    private static final String ROOT_URL = "/problem";

    public static Response createProblemRaw(String jwt, CreateProblemCommand command) {
        return BaseApiTest.given(jwt)
                .body(command)
                .when()
                .post(ROOT_URL + "/create");
    }

    public static CreateProblemResponse createProblem(String jwt, CreateProblemCommand command) {
        return createProblemRaw(jwt, command)
                .then()
                .statusCode(201)
                .extract()
                .as(CreateProblemResponse.class);
    }

    public static Response updateProblemSettingRaw(String jwt, String problemId, UpdateProblemSettingCommand command) {
        return BaseApiTest.given(jwt)
                .body(command)
                .when()
                .put(ROOT_URL + "/{problemId}/setting", problemId);
    }

    public static UpdateProblemResponse updateProblemSetting(String jwt, String problemId, UpdateProblemSettingCommand command) {
        return updateProblemSettingRaw(jwt, problemId, command)
                .then()
                .statusCode(200)
                .extract()
                .as(UpdateProblemResponse.class);
    }

    public static Response updateProblemTagsRaw(String jwt, String problemId, UpdateProblemTagsCommand command) {
        return BaseApiTest.given(jwt)
                .body(command)
                .when()
                .put(ROOT_URL + "/{problemId}/tags", problemId);
    }

    public static UpdateProblemTagsResponse updateProblemTags(String jwt, String problemId, UpdateProblemTagsCommand command) {
        return updateProblemTagsRaw(jwt, problemId, command)
                .then()
                .statusCode(200)
                .extract()
                .as(UpdateProblemTagsResponse.class);
    }

    public static Response removeProblemRaw(String jwt, String problemId) {
        return BaseApiTest.given(jwt)
                .when()
                .delete(ROOT_URL + "/{problemId}/remove", problemId);
    }

    public static void removeProblem(String jwt, String problemId) {
        removeProblemRaw(jwt, problemId)
                .then()
                .statusCode(200);
    }

}
