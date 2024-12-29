package org.ricky.common.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ricky.common.properties.EmailProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import static java.lang.String.format;
import static org.ricky.core.common.utils.MobileOrEmailUtils.maskMobileOrEmail;

@Slf4j
@Component
@Profile("prod")
@RequiredArgsConstructor
public class DefaultEmailSender implements MyEmailSender {

    private final JavaMailSender mailSender;
    private final EmailProperties emailProperties;

    @Override
    public void sendVerificationCode(String email, String code) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setFrom(format("%s <%s>", emailProperties.getFromName(), emailProperties.getFromEmail()));
            mailMessage.setSubject("验证码");
            mailMessage.setText(format("您的验证码为：%s，该验证码10分钟内有效，请勿泄露于他人。", code));
            mailSender.send(mailMessage);
            log.info("Sent email verification code to [{}].", maskMobileOrEmail(email));
        } catch (Throwable t) {
            log.error("Error while send notification code to email[{}].", email, t);
        }
    }

}
