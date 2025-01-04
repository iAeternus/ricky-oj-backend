package org.ricky.apiTest.core.user;

import org.junit.jupiter.api.Test;
import org.ricky.apiTest.BaseApiTest;
import org.ricky.apiTest.core.verification.VerificationCodeApi;
import org.ricky.core.user.alter.dto.command.RegisterCommand;
import org.ricky.core.user.alter.dto.response.RegisterResponse;
import org.ricky.core.user.domain.User;
import org.ricky.core.user.domain.event.UserCreatedEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ricky.common.domain.event.DomainEventTypeEnum.USER_CREATED;
import static org.ricky.common.exception.ErrorCodeEnum.*;
import static org.ricky.common.utils.RandomTestFixture.*;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/4
 * @className UserControllerApiTest
 * @desc
 */
public class UserControllerApiTest extends BaseApiTest {

    @Test
    public void should_register_with_mobile() {
        // Given
        String nickname = rNickname();
        String mobile = rMobile();

        String verificationCodeId = VerificationCodeApi.createVerificationCodeForRegister(mobile);
        String verificationCode = verificationCodeRepository.byId(verificationCodeId).getCode();

        RegisterCommand command = RegisterCommand.builder()
                .nickname(nickname)
                .password(rPassword())
                .mobileOrEmail(mobile)
                .verification(verificationCode)
                .agreement(true)
                .build();

        // When
        RegisterResponse response = UserApi.register(command);

        // Then
        User user = userRepository.byId(response.getUserId());
        assertNotNull(user);
        assertEquals(response.getUserId(), user.getId());
        assertEquals(nickname, user.getNickname());
        assertEquals(mobile, user.getMobile());
    }

    @Test
    public void should_register_with_email() {
        // Given
        String nickname = rNickname();
        String email = rEmail();

        String verificationCodeId = VerificationCodeApi.createVerificationCodeForRegister(email);
        String verificationCode = verificationCodeRepository.byId(verificationCodeId).getCode();

        RegisterCommand command = RegisterCommand.builder()
                .nickname(nickname)
                .password(rPassword())
                .mobileOrEmail(email)
                .verification(verificationCode)
                .agreement(true)
                .build();

        // When
        RegisterResponse response = UserApi.register(command);

        // Then
        User user = userRepository.byId(response.getUserId());
        assertNotNull(user);
        assertEquals(response.getUserId(), user.getId());
        assertEquals(nickname, user.getNickname());
        assertEquals(email, user.getEmail());
    }

    @Test
    public void should_fail_to_register_if_mobile_already_exists() {
        // Given
        String nickname = rNickname();
        String mobile = rMobile();

        String verificationCodeId = VerificationCodeApi.createVerificationCodeForRegister(mobile);
        String verificationCode = verificationCodeRepository.byId(verificationCodeId).getCode();

        RegisterCommand command = RegisterCommand.builder()
                .nickname(nickname)
                .password(rPassword())
                .mobileOrEmail(mobile)
                .verification(verificationCode)
                .agreement(true)
                .build();

        UserApi.register(command); // 先注册以占用手机号

        // When & Then
        assertError(() -> UserApi.registerRaw(command), USER_WITH_MOBILE_OR_EMAIL_ALREADY_EXISTS);
    }

    @Test
    public void should_fail_to_register_if_email_already_exists() {
        // Given
        String nickname = rNickname();
        String email = rEmail();

        String verificationCodeId = VerificationCodeApi.createVerificationCodeForRegister(email);
        String verificationCode = verificationCodeRepository.byId(verificationCodeId).getCode();

        RegisterCommand command = RegisterCommand.builder()
                .nickname(nickname)
                .password(rPassword())
                .mobileOrEmail(email)
                .verification(verificationCode)
                .agreement(true)
                .build();

        UserApi.register(command); // 先注册以占用手机号

        // When & Then
        assertError(() -> UserApi.registerRaw(command), USER_WITH_MOBILE_OR_EMAIL_ALREADY_EXISTS);
    }

    @Test
    public void should_fail_to_register_if_verification_not_valid() {
        // Given
        String nickname = rNickname();
        String email = rEmail();

        RegisterCommand command = RegisterCommand.builder()
                .nickname(nickname)
                .password(rPassword())
                .mobileOrEmail(email)
                .verification(rVerificationCode())
                .agreement(true)
                .build();

        assertError(() -> UserApi.registerRaw(command), VERIFICATION_CODE_CHECK_FAILED);
    }

    @Test
    public void should_fail_to_register_if_not_agree_agreement() {
        // Given
        String nickname = rNickname();
        String email = rEmail();

        RegisterCommand command = RegisterCommand.builder()
                .nickname(nickname)
                .password(rPassword())
                .mobileOrEmail(email)
                .verification(rVerificationCode())
                .agreement(false)
                .build();

        assertError(() -> UserApi.registerRaw(command), MUST_SIGN_AGREEMENT);
    }

    @Test
    public void should_raise_user_created_event_after_register() {
        // Given
        String nickname = rNickname();
        String mobile = rMobile();

        String verificationCodeId = VerificationCodeApi.createVerificationCodeForRegister(mobile);
        String verificationCode = verificationCodeRepository.byId(verificationCodeId).getCode();

        RegisterCommand command = RegisterCommand.builder()
                .nickname(nickname)
                .password(rPassword())
                .mobileOrEmail(mobile)
                .verification(verificationCode)
                .agreement(true)
                .build();

        // When
        RegisterResponse response = UserApi.register(command);

        // Then
        UserCreatedEvent theEvent = domainEventDao.latestEventFor(response.getUserId(), USER_CREATED, UserCreatedEvent.class);
        assertEquals(response.getUserId(), theEvent.getUserId());
    }

}
