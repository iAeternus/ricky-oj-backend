package org.ricky.core.verification;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.ricky.common.constants.CommonConstants;
import org.ricky.common.ratelimit.RateLimiter;
import org.ricky.core.MockMvcUtils;
import org.ricky.core.common.domain.ReturnId;
import org.ricky.core.user.domain.UserRepository;
import org.ricky.core.verification.alter.VerificationCodeAlterService;
import org.ricky.core.verification.alter.dto.command.CreateRegisterVerificationCodeCommand;
import org.ricky.core.verification.domain.VerificationCodeFactory;
import org.ricky.core.verification.domain.VerificationCodeRepository;
import org.ricky.core.verification.domain.VerificationCodeSender;
import org.ricky.core.verification.query.VerificationCodeQueryService;
import org.ricky.management.SystemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.ricky.common.constants.CommonConstants.VERIFICATION_ID_PREFIX;
import static org.ricky.common.utils.RandomTestFixture.rMobile;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

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
    private VerificationCodeAlterService verificationCodeAlterService;

    @Mock
    private VerificationCodeQueryService verificationCodeQueryService;

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

        when(verificationCodeAlterService.createVerificationCodeForRegister(command)).thenReturn(verificationId);

        // When
        ReturnId returnId = verificationController.createVerificationCodeForRegister(command);

        // Then
        assertEquals(verificationId, returnId.toString());
    }

}