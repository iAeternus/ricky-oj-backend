package org.ricky.apiTest.utils;

import org.ricky.apiTest.core.login.LoginApi;
import org.ricky.apiTest.core.user.UserApi;
import org.ricky.apiTest.core.verification.VerificationCodeApi;
import org.ricky.common.domain.LoginResponse;
import org.ricky.core.user.alter.command.RegisterCommand;
import org.ricky.core.user.alter.response.RegisterResponse;
import org.ricky.core.verification.domain.VerificationCodeRepository;
import org.springframework.stereotype.Component;

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
        String verificationCodeId = VerificationCodeApi.createVerificationCodeForRegister(mobileOrEmail);
        String code = verificationCodeRepository.byId(verificationCodeId).getCode();

        RegisterCommand command = RegisterCommand.builder()
                .nickname(nickname)
                .password(password)
                .mobileOrEmail(mobileOrEmail)
                .verification(code)
                .agreement(true)
                .build();

        return UserApi.register(command);
    }

    public RegisterResponse register(String mobileOrEmail, String password) {
        return register(rNickname(), password, mobileOrEmail);
    }

    public RegisterResponse register() {
        return register(rMobile(), rPassword());
    }

    public LoginResponse registerWithLogin(String mobileOrEmail, String password) {
        RegisterResponse response = register(mobileOrEmail, password);
        String jwt = LoginApi.loginWithMobileOrEmail(mobileOrEmail, password);
        return new LoginResponse(response.getUserId(), jwt);
    }

    public LoginResponse registerWithLogin(String memberName, String mobileOrEmail, String password) {
        RegisterResponse response = register(memberName, mobileOrEmail, password);
        String jwt = LoginApi.loginWithMobileOrEmail(mobileOrEmail, password);
        return new LoginResponse(response.getUserId(), jwt);
    }

    public LoginResponse registerWithLogin() {
        return registerWithLogin(rMobile(), rPassword());
    }

}

