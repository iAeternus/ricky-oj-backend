package org.ricky.apiTest.utils;

import org.ricky.apiTest.core.user.UserApi;
import org.ricky.apiTest.core.verification.VerificationCodeApi;
import org.ricky.core.user.alter.dto.command.RegisterCommand;
import org.ricky.core.user.alter.dto.response.RegisterResponse;
import org.ricky.core.verification.domain.VerificationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.ricky.common.utils.RandomTestFixture.*;


/**
 * @author Ricky
 * @version 1.0
 * @date 2024/11/12
 * @className SetupApi
 * @desc
 */
@Component
public class SetupApi {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

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
        return register(rNickname(), mobileOrEmail, password);
    }

    public RegisterResponse register() {
        return register(rMobile(), rPassword());
    }

    // public JwtTokenResponse registerWithLogin(String mobile, String password) {
    //     UserRegisterResponse response = register(mobile, password);
    //     UserLoginCommand command = UserLoginCommand.builder()
    //             .mobile(mobile)
    //             .password(password)
    //             .build();
    //     return UserApi.login(command);
    // }
    //
    // public JwtTokenResponse registerWithLogin() {
    //     return registerWithLogin(rMobile(), rPassword());
    // }

}
