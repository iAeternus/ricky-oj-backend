package org.ricky.common.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ricky.common.properties.AliyunProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.aliyuncs.http.MethodType.POST;
import static org.ricky.common.utils.ValidationUtils.isNull;
import static org.ricky.core.common.utils.MobileOrEmailUtils.maskMobileOrEmail;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/29
 * @className AliyunSmsSender
 * @desc
 */
@Slf4j
@Component
@Profile("prod")
@RequiredArgsConstructor
public class AliyunSmsSender implements MySmsSender {

    private final AliyunProperties aliyunProperties;
    private final AliyunProperties.SmsProperties smsProperties;
    private IAcsClient acsClient;

    @Override
    public boolean sendVerificationCode(String mobile, String code) {
        SendSmsRequest request = new SendSmsRequest();
        request.setSysMethod(POST);
        request.setPhoneNumbers(mobile);
        request.setSignName(smsProperties.getSmsSignName());
        request.setTemplateCode(smsProperties.getSmsTemplateCode());
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        try {
            SendSmsResponse response = getAcsClient().getAcsResponse(request);
            if ("OK".equalsIgnoreCase(response.getCode())) {
                log.info("Sent SMS verification code to [{}].", maskMobileOrEmail(mobile));
                return true;
            } else {
                log.error("Failed to send verification code to mobile[{}]: {}.", maskMobileOrEmail(mobile), response.getMessage());
                return false;
            }
        } catch (Throwable t) {
            log.error("Failed to send verification code to mobile[{}].", maskMobileOrEmail(mobile), t);
            return false;
        }
    }

    private IAcsClient getAcsClient() {
        if (isNull(acsClient)) {
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliyunProperties.getAk(), aliyunProperties.getAks());
            DefaultProfile.addEndpoint("cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
            acsClient = new DefaultAcsClient(profile);
        }

        return acsClient;
    }

}
