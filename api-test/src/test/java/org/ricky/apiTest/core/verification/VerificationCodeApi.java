package org.ricky.apiTest.core.verification;

import io.restassured.response.Response;
import org.ricky.apiTest.BaseApiTest;
import org.ricky.core.common.domain.ReturnId;
import org.ricky.core.verification.alter.dto.command.CreateLoginVerificationCodeCommand;
import org.ricky.core.verification.alter.dto.command.CreateRegisterVerificationCodeCommand;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/30
 * @className VerificationCodeApi
 * @desc
 */
public class VerificationCodeApi {

    public static Response createVerificationCodeForRegisterRaw(CreateRegisterVerificationCodeCommand command) {
        return BaseApiTest.given()
                .body(command)
                .when()
                .post("/verification-code/for-register");
    }

    public static String createVerificationCodeForRegister(CreateRegisterVerificationCodeCommand command) {
        return createVerificationCodeForRegisterRaw(command)
                .then()
                .statusCode(201)
                .extract()
                .as(ReturnId.class)
                .toString();
    }

    public static String createVerificationCodeForRegister(String mobileOrEmail) {
        return createVerificationCodeForRegister(CreateRegisterVerificationCodeCommand.builder().mobileOrEmail(mobileOrEmail).build());
    }

    public static Response createVerificationCodeForLoginRaw(CreateLoginVerificationCodeCommand command) {
        return BaseApiTest.given()
                .body(command)
                .when()
                .post("/verification-code/for-login");
    }

    public static String createVerificationCodeForLogin(CreateLoginVerificationCodeCommand command) {
        return createVerificationCodeForLoginRaw(command)
                .then()
                .statusCode(201)
                .extract()
                .as(ReturnId.class).toString();
    }

}
