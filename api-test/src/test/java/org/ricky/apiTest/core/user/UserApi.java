package org.ricky.apiTest.core.user;

import io.restassured.response.Response;
import org.ricky.apiTest.BaseApiTest;
import org.ricky.core.user.alter.dto.command.RegisterCommand;
import org.ricky.core.user.alter.dto.response.RegisterResponse;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/30
 * @className UserApi
 * @desc
 */
public class UserApi {

    private static final String ROOT_URL = "/user";

    public static Response registerRaw(RegisterCommand command) {
        return BaseApiTest.given()
                .body(command)
                .when()
                .post(ROOT_URL + "/registration");
    }

    public static RegisterResponse register(RegisterCommand command) {
        return registerRaw(command)
                .then()
                .statusCode(201)
                .extract()
                .as(RegisterResponse.class);
    }

}
