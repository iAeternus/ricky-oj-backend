package org.ricky.core.verification.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ricky.common.email.MyEmailSender;
import org.ricky.common.sms.MySmsSender;
import org.ricky.core.verification.domain.VerificationCode;
import org.ricky.core.verification.domain.VerificationCodeSender;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import static org.ricky.core.common.utils.MobileOrEmailUtils.isMobileNumber;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/9/23
 * @className DefaultVerificationCodeSender
 * @desc 默认验证码发送器
 */
@Slf4j
@Component
// @Profile("prod")
@RequiredArgsConstructor
public class DefaultVerificationCodeSender implements VerificationCodeSender {

    private final TaskExecutor taskExecutor;
    private final MyEmailSender emailSender;
    private final MySmsSender smsSender;

    @Override
    public void send(VerificationCode code) {
        String mobileOrEmail = code.getMobileOrEmail();
        String theCode = code.getCode();

        if (isMobileNumber(mobileOrEmail)) {
            taskExecutor.execute(() -> {
                boolean result = smsSender.sendVerificationCode(mobileOrEmail, theCode);
                // TODO 统计短信发送量
            });
        } else {
            taskExecutor.execute(() -> emailSender.sendVerificationCode(mobileOrEmail, theCode));
        }
    }
}
