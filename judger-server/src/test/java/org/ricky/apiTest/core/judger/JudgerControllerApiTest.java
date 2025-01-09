package org.ricky.apiTest.core.judger;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.ricky.apiTest.BaseApiTest;
import org.ricky.common.domain.LoginResponse;
import org.ricky.core.judger.fetch.response.FetchJudgerInfoResponse;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.ricky.common.utils.ValidationUtils.isNotEmpty;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/7
 * @className JudgerControllerApiTest
 * @desc
 */
@Slf4j
public class JudgerControllerApiTest extends BaseApiTest {

    @Test
    public void should_fetch_judger_info() {
        // Given
        LoginResponse operator = setupApi.registerWithLogin();

        // When
        FetchJudgerInfoResponse response = JudgerApi.fetchJudgerInfo(operator.getJwt());

        // Then
        log.info("{}", objectMapper.writeValueAsString(response));
        assertTrue(isNotEmpty(response.getInfo()));
    }

}
