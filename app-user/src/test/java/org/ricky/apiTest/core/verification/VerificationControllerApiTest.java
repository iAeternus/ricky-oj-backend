package org.ricky.apiTest.core.verification;

import org.junit.jupiter.api.Test;
import org.ricky.apiTest.BaseApiTest;
import org.ricky.core.verification.alter.command.CreateLoginVerificationCodeCommand;
import org.ricky.core.verification.alter.command.CreateRegisterVerificationCodeCommand;
import org.ricky.core.verification.domain.VerificationCode;

import static org.junit.jupiter.api.Assertions.*;
import static org.ricky.apiTest.core.verification.VerificationCodeApi.createVerificationCodeForLogin;
import static org.ricky.apiTest.core.verification.VerificationCodeApi.createVerificationCodeForRegister;
import static org.ricky.common.utils.RandomTestFixture.*;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/4
 * @className VerificationControllerApiTest
 * @desc
 */
public class VerificationControllerApiTest extends BaseApiTest {

    @Test
    public void should_create_verification_code_for_register() {
        // Given
        String mobile = rMobile();
        CreateRegisterVerificationCodeCommand command = CreateRegisterVerificationCodeCommand.builder().mobileOrEmail(mobile).build();

        // When
        String returnId = createVerificationCodeForRegister(command);

        // Then
        VerificationCode verificationCode = verificationCodeRepository.byId(returnId);
        assertNotNull(verificationCode);
        assertEquals(mobile, verificationCode.getMobileOrEmail());
    }


    @Test
    public void should_fail_create_verification_code_for_register_if_mobile_is_occupied() {
        // Given
        String mobile = rMobile();
        String password = rPassword();
        setupApi.register(mobile, password);

        // When
        CreateRegisterVerificationCodeCommand command = CreateRegisterVerificationCodeCommand.builder().mobileOrEmail(mobile).build();

        // Then
        assertFalse(verificationCodeRepository.exists(createVerificationCodeForRegister(command)));
    }


    @Test
    public void should_fail_create_verification_code_for_register_if_email_is_occupied() {
        // Given
        String email = rEmail();
        String password = rPassword();
        setupApi.register(email, password);

        // When
        CreateRegisterVerificationCodeCommand command = CreateRegisterVerificationCodeCommand.builder().mobileOrEmail(email).build();

        // Then
        assertFalse(verificationCodeRepository.exists(createVerificationCodeForRegister(command)));
    }

    @Test
    public void should_create_verification_code_for_login() {
        // Given
        String mobile = rMobile();
        String password = rPassword();
        setupApi.register(mobile, password);
        CreateLoginVerificationCodeCommand command = CreateLoginVerificationCodeCommand.builder().mobileOrEmail(mobile).build();

        // When
        String returnId = createVerificationCodeForLogin(command);

        // Then
        VerificationCode verificationCode = verificationCodeRepository.byId(returnId);
        assertNotNull(verificationCode);
        assertEquals(mobile, verificationCode.getMobileOrEmail());
    }

}
