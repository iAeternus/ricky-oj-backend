package org.ricky.apiTest.core.login;

import org.junit.jupiter.api.Test;
import org.ricky.apiTest.BaseApiTest;
import org.ricky.apiTest.core.user.UserApi;
import org.ricky.apiTest.core.verification.VerificationCodeApi;
import org.ricky.core.login.alter.command.VerificationCodeLoginCommand;
import org.ricky.core.user.fetch.response.UserInfoResponse;
import org.ricky.core.verification.alter.command.CreateLoginVerificationCodeCommand;
import org.ricky.core.verification.domain.VerificationCode;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ricky.common.utils.RandomTestFixture.*;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/4
 * @className LoginControllerApiTest
 * @desc
 */
public class LoginControllerApiTest extends BaseApiTest {

    @Test
    public void should_login_with_mobile() {
        // Given
        String mobile = rMobile();
        String password = rPassword();
        setupApi.register(mobile, password);

        // When
        String jwt = LoginApi.loginWithMobileOrEmail(mobile, password);

        // Then
        UserInfoResponse response = UserApi.fetchMyUserInfo(jwt);
        assertNotNull(response);
    }

    @Test
    public void should_login_with_email() {
        // Given
        String email = rEmail();
        String password = rPassword();
        setupApi.register(email, password);

        // When
        String jwt = LoginApi.loginWithMobileOrEmail(email, password);

        // Then
        UserInfoResponse response = UserApi.fetchMyUserInfo(jwt);
        assertNotNull(response);

    }

    @Test
    public void should_login_with_verification_code() {
        // Given
        String mobile = rMobile();
        setupApi.register(mobile, rPassword());
        String codeId = VerificationCodeApi.createVerificationCodeForLogin(CreateLoginVerificationCodeCommand.builder().mobileOrEmail(mobile).build());
        VerificationCode code = verificationCodeRepository.byId(codeId);
        VerificationCodeLoginCommand command = VerificationCodeLoginCommand.builder().mobileOrEmail(mobile).verification(code.getCode()).build();

        // When
        String jwt = LoginApi.loginWithVerificationCode(command);

        // Then
        UserInfoResponse response = UserApi.fetchMyUserInfo(jwt);
        assertNotNull(response);
    }

}
