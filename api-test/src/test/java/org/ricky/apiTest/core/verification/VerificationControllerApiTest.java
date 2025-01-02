package org.ricky.apiTest.core.verification;

import org.junit.jupiter.api.Test;
import org.ricky.apiTest.BaseApiTest;
import org.ricky.core.user.alter.dto.response.RegisterResponse;
import org.ricky.core.verification.alter.dto.command.CreateLoginVerificationCodeCommand;
import org.ricky.core.verification.alter.dto.command.CreateRegisterVerificationCodeCommand;
import org.ricky.core.verification.domain.VerificationCode;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.ricky.apiTest.core.verification.VerificationCodeApi.createVerificationCodeForLogin;
import static org.ricky.apiTest.core.verification.VerificationCodeApi.createVerificationCodeForRegister;
import static org.ricky.common.utils.RandomTestFixture.*;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/30
 * @className VerificationControllerApiTest
 * @desc
 */
public class VerificationControllerApiTest extends BaseApiTest {

    @Test
    public void should_create_verification_code_for_register() {
        String mobile = rMobile();

        CreateRegisterVerificationCodeCommand command = CreateRegisterVerificationCodeCommand.builder().mobileOrEmail(mobile).build();
        String returnId = createVerificationCodeForRegister(command);
        VerificationCode verificationCode = verificationCodeRepository.byId(returnId);

        assertNotNull(verificationCode);
        assertEquals(mobile, verificationCode.getMobileOrEmail());
    }

    @Test
    public void should_fail_create_verification_code_for_register_if_mobile_is_occupied() {
        String mobile = rMobile();
        String password = rPassword();
        setupApi.register(mobile, password);

        CreateRegisterVerificationCodeCommand command = CreateRegisterVerificationCodeCommand.builder().mobileOrEmail(mobile).build();
        assertFalse(verificationCodeRepository.exists(createVerificationCodeForRegister(command)));
    }

    @Test
    public void should_fail_create_verification_code_for_register_if_email_is_occupied() {
        String email = rEmail();
        String password = rPassword();
        setupApi.register(email, password);

        CreateRegisterVerificationCodeCommand command = CreateRegisterVerificationCodeCommand.builder().mobileOrEmail(email).build();
        assertFalse(verificationCodeRepository.exists(createVerificationCodeForRegister(command)));
    }

    @Test
    public void should_create_verification_code_for_login() {
        String mobile = rMobile();
        String password = rPassword();
        RegisterResponse response = setupApi.register(mobile, password);

        CreateLoginVerificationCodeCommand command = CreateLoginVerificationCodeCommand.builder().mobileOrEmail(mobile).build();
        String returnId = createVerificationCodeForLogin(command);
        VerificationCode verificationCode = verificationCodeRepository.byId(returnId);

        assertNotNull(verificationCode);
        assertEquals(mobile, verificationCode.getMobileOrEmail());
    }

}
