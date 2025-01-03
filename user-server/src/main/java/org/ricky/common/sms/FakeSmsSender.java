package org.ricky.common.sms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/29
 * @className FakeSmsSender
 * @desc
 */
@Slf4j
@Component
@Profile("!prod")
@RequiredArgsConstructor
public class FakeSmsSender implements MySmsSender {

    @Override
    public boolean sendVerificationCode(String mobile, String code) {
        log.info("Verification code for {} is {}", mobile, code);
        return true;
    }

}
