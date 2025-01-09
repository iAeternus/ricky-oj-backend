package org.ricky.apiTest.core.judge;

import io.restassured.response.Response;
import org.ricky.apiTest.BaseApiTest;
import org.ricky.core.judge.alter.command.ModifyStatusCommand;
import org.ricky.core.judge.alter.command.SubmitCommand;
import org.ricky.core.judge.alter.response.SubmitResponse;
import org.ricky.core.judge.fetch.response.FetchJudgeByIdResponse;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/9
 * @className JudgeApi
 * @desc
 */
public class JudgeApi {

    private static final String ROOT_URL = "/judge";

    public static Response submitRaw(String jwt, SubmitCommand command) {
        return BaseApiTest.given(jwt)
                .body(command)
                .when()
                .post(ROOT_URL + "/submit");
    }

    public static SubmitResponse submit(String jwt, SubmitCommand command) {
        return submitRaw(jwt, command)
                .then()
                .statusCode(201)
                .extract()
                .as(SubmitResponse.class);
    }

    public static Response modifyStatusRaw(String jwt, ModifyStatusCommand command) {
        return BaseApiTest.given(jwt)
                .body(command)
                .when()
                .put(ROOT_URL + "/modify/status");
    }

    public static void modifyStatus(String jwt, ModifyStatusCommand command) {
        modifyStatusRaw(jwt, command)
                .then()
                .statusCode(200);
    }

    public static FetchJudgeByIdResponse fetchJudgeById(String jwt, String judgeId) {
        return BaseApiTest.given(jwt)
                .when()
                .get(ROOT_URL + "/{judgeId}", judgeId)
                .then()
                .statusCode(200)
                .extract()
                .as(FetchJudgeByIdResponse.class);
    }

}
