package org.ricky.core.verification;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.ricky.core.common.domain.ReturnId;
import org.ricky.core.verification.alter.VerificationCodeAlterationService;
import org.ricky.core.verification.alter.dto.command.CreateRegisterVerificationCodeCommand;
import org.ricky.core.verification.fetch.VerificationCodeFetchService;
import org.ricky.management.SystemManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.ricky.common.constants.CommonConstants.VERIFICATION_ID_PREFIX;
import static org.ricky.common.utils.RandomTestFixture.rMobile;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/31
 * @className VerificationControllerTest
 * @desc
 */
@WebMvcTest(VerificationController.class)
class VerificationControllerTest {


    @InjectMocks
    private VerificationController verificationController;

    @Mock
    private VerificationCodeAlterationService verificationCodeAlterationService;

    @Mock
    private VerificationCodeFetchService verificationCodeFetchService;

    private static final String ROOT_URL = "/verification-code";

    private static String verificationId;

    @BeforeAll
    public static void init() {
        verificationId = SystemManager.baseId(VERIFICATION_ID_PREFIX);
    }

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

        when(verificationCodeAlterationService.createVerificationCodeForRegister(command)).thenReturn(verificationId);

        // When
        ReturnId returnId = verificationController.createVerificationCodeForRegister(command);

        // Then
        assertEquals(verificationId, returnId.toString());
    }

}