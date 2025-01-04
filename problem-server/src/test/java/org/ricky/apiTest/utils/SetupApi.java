package org.ricky.apiTest.utils;

import io.restassured.http.Cookie;
import io.restassured.response.Response;
import org.ricky.apiTest.BaseApiTest;
import org.ricky.common.domain.LoginResponse;
import org.ricky.core.common.domain.ReturnId;
import org.ricky.core.login.alter.dto.command.MobileOrEmailLoginCommand;
import org.ricky.core.user.alter.dto.command.RegisterCommand;
import org.ricky.core.user.alter.dto.response.RegisterResponse;
import org.ricky.core.verification.alter.dto.command.CreateRegisterVerificationCodeCommand;
import org.ricky.core.verification.domain.VerificationCodeRepository;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ricky.common.constants.CommonConstants.AUTH_COOKIE_NAME;
import static org.ricky.common.utils.RandomTestFixture.*;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/4
 * @className SetupApi
 * @desc
 */
@Component
public class SetupApi {

    private final VerificationCodeRepository verificationCodeRepository;

    public SetupApi(VerificationCodeRepository verificationCodeRepository) {
        this.verificationCodeRepository = verificationCodeRepository;
    }

    public RegisterResponse register(String nickname, String password, String mobileOrEmail) {
        String verificationCodeId = createVerificationCodeForRegister(mobileOrEmail);
        String code = verificationCodeRepository.byId(verificationCodeId).getCode();

        RegisterCommand command = RegisterCommand.builder()
                .nickname(nickname)
                .password(password)
                .mobileOrEmail(mobileOrEmail)
                .verification(code)
                .agreement(true)
                .build();

        return register(command);
    }

    public RegisterResponse register(String mobileOrEmail, String password) {
        return register(rNickname(), password, mobileOrEmail);
    }

    public RegisterResponse register() {
        return register(rMobile(), rPassword());
    }

    public LoginResponse registerWithLogin(String mobileOrEmail, String password) {
        RegisterResponse response = register(mobileOrEmail, password);
        String jwt = loginWithMobileOrEmail(mobileOrEmail, password);
        return new LoginResponse(response.getUserId(), jwt);
    }

    public LoginResponse registerWithLogin(String memberName, String mobileOrEmail, String password) {
        RegisterResponse response = register(memberName, mobileOrEmail, password);
        String jwt = loginWithMobileOrEmail(mobileOrEmail, password);
        return new LoginResponse(response.getUserId(), jwt);
    }

    public LoginResponse registerWithLogin() {
        return registerWithLogin(rMobile(), rPassword());
    }

    private static Response loginWithMobileOrEmailRaw(MobileOrEmailLoginCommand command) {
        return BaseApiTest.given()
                .body(command)
                .when()
                .post("/login");
    }

    private static String loginWithMobileOrEmail(MobileOrEmailLoginCommand command) {
        Cookie cookie = loginWithMobileOrEmailRaw(command)
                .then()
                .statusCode(200)
                .extract()
                .detailedCookie(AUTH_COOKIE_NAME);

        assertNotNull(cookie);
        assertNotNull(cookie.getValue());
        assertEquals("/", cookie.getPath());
        assertNotNull(cookie.getDomain());
        return cookie.getValue();
    }

    private static String loginWithMobileOrEmail(String mobileOrEmail, String password) {
        MobileOrEmailLoginCommand command = MobileOrEmailLoginCommand.builder()
                .mobileOrEmail(mobileOrEmail)
                .password(password)
                .build();

        return loginWithMobileOrEmail(command);
    }

    private static Response registerRaw(RegisterCommand command) {
        return BaseApiTest.given()
                .body(command)
                .when()
                .post("user/registration");
    }

    private static RegisterResponse register(RegisterCommand command) {
        return registerRaw(command)
                .then()
                .statusCode(201)
                .extract()
                .as(RegisterResponse.class);
    }

    private static Response createVerificationCodeForRegisterRaw(CreateRegisterVerificationCodeCommand command) {
        return BaseApiTest.given()
                .body(command)
                .when()
                .post("/verification-code/for-register");
    }

    private static String createVerificationCodeForRegister(CreateRegisterVerificationCodeCommand command) {
        return createVerificationCodeForRegisterRaw(command)
                .then()
                .statusCode(201)
                .extract()
                .as(ReturnId.class)
                .toString();
    }

    private static String createVerificationCodeForRegister(String mobileOrEmail) {
        return createVerificationCodeForRegister(CreateRegisterVerificationCodeCommand.builder().mobileOrEmail(mobileOrEmail).build());
    }

}

