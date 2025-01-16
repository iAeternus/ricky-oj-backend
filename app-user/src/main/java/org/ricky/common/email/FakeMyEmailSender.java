package org.ricky.common.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("!prod")
public class FakeMyEmailSender implements MyEmailSender {
    @Override
    public void sendVerificationCode(String email, String code) {
        log.info("Verification code for {} is {}", email, code);
    }
}
