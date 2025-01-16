package org.ricky.apiTest.core.user;

import io.restassured.response.Response;
import org.ricky.apiTest.BaseApiTest;
import org.ricky.core.user.alter.command.RegisterCommand;
import org.ricky.core.user.alter.response.RegisterResponse;
import org.ricky.core.user.fetch.response.UserInfoResponse;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/4
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

    public static UserInfoResponse fetchMyUserInfo(String jwt) {
        return BaseApiTest.given(jwt)
                .when()
                .get(ROOT_URL + "/my/info")
                .then()
                .statusCode(200)
                .extract()
                .as(UserInfoResponse.class);
    }

}
