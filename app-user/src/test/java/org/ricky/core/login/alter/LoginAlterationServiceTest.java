package org.ricky.core.login.alter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/16
 * @className LoginAlterationServiceTest
 * @desc
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
class LoginAlterationServiceTest {

    @Autowired
    private LoginAlterationService loginAlterationService;

    // @Test
    // public void should_login_with_mobile() {
    //     // Given
    //     String mobile = rMobile();
    //     String password = rPassword();
    //     setupApi.register(mobile, password);
    //
    //     // When
    //     String jwt = LoginApi.loginWithMobileOrEmail(mobile, password);
    //
    //     // Then
    //     UserInfoResponse response = UserApi.fetchMyUserInfo(jwt);
    //     assertNotNull(response);
    // }
    //
    // @Test
    // public void should_login_with_email() {
    //     // Given
    //     String email = rEmail();
    //     String password = rPassword();
    //     setupApi.register(email, password);
    //
    //     // When
    //     String jwt = LoginApi.loginWithMobileOrEmail(email, password);
    //
    //     // Then
    //     UserInfoResponse response = UserApi.fetchMyUserInfo(jwt);
    //     assertNotNull(response);
    //
    // }
    //
    // @Test
    // public void should_login_with_verification_code() {
    //     // Given
    //     String mobile = rMobile();
    //     setupApi.register(mobile, rPassword());
    //     String codeId = VerificationCodeApi.createVerificationCodeForLogin(CreateLoginVerificationCodeCommand.builder().mobileOrEmail(mobile).build());
    //     VerificationCode code = verificationCodeRepository.byId(codeId);
    //     VerificationCodeLoginCommand command = VerificationCodeLoginCommand.builder().mobileOrEmail(mobile).verification(code.getCode()).build();
    //
    //     // When
    //     String jwt = LoginApi.loginWithVerificationCode(command);
    //
    //     // Then
    //     UserInfoResponse response = UserApi.fetchMyUserInfo(jwt);
    //     assertNotNull(response);
    // }

}