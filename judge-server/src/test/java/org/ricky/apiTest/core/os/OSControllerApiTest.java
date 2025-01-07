package org.ricky.apiTest.core.os;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.ricky.apiTest.BaseApiTest;
import org.ricky.common.domain.LoginResponse;
import org.ricky.common.utils.ValidationUtils;
import org.ricky.core.os.fetch.response.FetchOSConfigResponse;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.ricky.common.utils.ValidationUtils.isNotEmpty;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/7
 * @className OSControllerApiTest
 * @desc
 */
@Slf4j
public class OSControllerApiTest extends BaseApiTest {

    @Test
    public void should_fetch_os_config() {
        // Given
        LoginResponse operator = setupApi.registerWithLogin();

        // When
        FetchOSConfigResponse response = OSApi.fetchOSConfig(operator.getJwt());

        // Then
        log.info("{}", objectMapper.writeValueAsString(response));
        assertTrue(isNotEmpty(response.getInfo()));
    }

}
