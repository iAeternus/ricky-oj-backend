package org.ricky.apiTest.core.login;

import io.restassured.http.Cookie;
import io.restassured.response.Response;
import org.ricky.apiTest.BaseApiTest;
import org.ricky.core.login.alter.command.MobileOrEmailLoginCommand;
import org.ricky.core.login.alter.command.VerificationCodeLoginCommand;

import static org.junit.jupiter.api.Assertions.*;
import static org.ricky.common.constants.CommonConstants.AUTH_COOKIE_NAME;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/4
 * @className LoginApi
 * @desc
 */
public class LoginApi {

    public static Response loginWithMobileOrEmailRaw(MobileOrEmailLoginCommand command) {
        return BaseApiTest.given()
                .body(command)
                .when()
                .post("/login");
    }

    public static String loginWithMobileOrEmail(MobileOrEmailLoginCommand command) {
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

    public static String loginWithMobileOrEmail(String mobileOrEmail, String password) {
        MobileOrEmailLoginCommand command = MobileOrEmailLoginCommand.builder()
                .mobileOrEmail(mobileOrEmail)
                .password(password)
                .build();

        return loginWithMobileOrEmail(command);
    }

    public static Response loginWithVerificationCodeRaw(VerificationCodeLoginCommand command) {
        return BaseApiTest.given()
                .body(command)
                .when()
                .post("/verification-code-login");
    }

    public static String loginWithVerificationCode(VerificationCodeLoginCommand command) {
        Cookie cookie = loginWithVerificationCodeRaw(command)
                .then()
                .statusCode(200)
                .extract()
                .detailedCookie(AUTH_COOKIE_NAME);

        assertNotNull(cookie);
        String jwt = cookie.getValue();
        assertNotNull(jwt);
        assertEquals("/", cookie.getPath());
        assertNotNull(cookie.getDomain());
        return jwt;
    }

    public static void logout() {
        Cookie cookie = BaseApiTest.given().when()
                .delete("/logout").then()
                .statusCode(200)
                .extract()
                .detailedCookie(AUTH_COOKIE_NAME);

        assertNotNull(cookie);
        assertEquals("", cookie.getValue());
        assertEquals(0, cookie.getMaxAge());
    }

    public static String refreshToken(String jwt) {
        Cookie cookie = BaseApiTest.given(jwt).when()
                .put("/refresh-token")
                .then()
                .statusCode(200)
                .extract()
                .detailedCookie(AUTH_COOKIE_NAME);

        assertNotNull(cookie);
        String refreshJwt = cookie.getValue();

        assertNotNull(refreshJwt);
        assertNotEquals(jwt, refreshJwt);
        return refreshJwt;
    }

}
