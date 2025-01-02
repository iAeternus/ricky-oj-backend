package org.ricky.core.verification.alter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.ricky.common.context.UserContext;
import org.ricky.common.ratelimit.RateLimiter;
import org.ricky.core.user.domain.UserRepository;
import org.ricky.core.verification.alter.dto.command.CreateRegisterVerificationCodeCommand;
import org.ricky.core.verification.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.ricky.common.context.UserContext.NOUSER;
import static org.ricky.common.utils.RandomTestFixture.rMobile;
import static org.ricky.core.verification.domain.VerificationCodeTypeEnum.REGISTER;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/31
 * @className VerificationCodeAlterServiceTest
 * @desc
 */
class VerificationCodeAlterServiceTest {

    @InjectMocks
    private VerificationCodeAlterService verificationCodeAlterService;

    @Mock
    private VerificationCodeRepository verificationCodeRepository;
    @Mock
    private VerificationCodeFactory verificationCodeFactory;
    @Mock
    private VerificationCodeSender verificationCodeSender;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RateLimiter rateLimiter;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    void should_create_verification_code_for_register() {
        // Given
        String mobile = rMobile();
        CreateRegisterVerificationCodeCommand command = CreateRegisterVerificationCodeCommand.builder()
                .mobileOrEmail(mobile)
                .build();

        when(userRepository.existsByMobileOrEmail(mobile)).thenReturn(false);
        doNothing().when(verificationCodeRepository).save(any());
        when(verificationCodeFactory.create(mobile, REGISTER, null, NOUSER)).thenCallRealMethod();
        when(verificationCodeRepository.existsWithinOneMinutes(mobile, REGISTER)).thenReturn(false);
        when(verificationCodeRepository.totalCodeCountOfTodayFor(mobile)).thenReturn(1L);

        // When
        String verificationCodeId = verificationCodeAlterService.createVerificationCodeForRegister(command);

        // Then
        verify(verificationCodeSender, times(1)).send(any());

        assertNotNull(verificationCodeId);
    }

    @Test
    void should_fail_create_verification_code_for_register_if_mobile_is_occupied() {

    }

}