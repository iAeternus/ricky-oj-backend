package org.ricky.common.properties;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Component
@Validated
@ConfigurationProperties("my.aliyun")
public class AliyunProperties {

    @NotBlank
    private String ak;

    @NotBlank
    private String aks;

    @NotBlank
    private String role;

    @Data
    @Component
    @Validated
    @ConfigurationProperties("my.aliyun.oss")
    public static class OssProperties {
        private String ossBucket;
        private String ossEndpoint;
        private boolean syncSubdomain;
        private String deliveryQueryAppCode;
        private String ossUtilCommand;
        private String ossUtilConfigFile;
    }

    @Data
    @Component
    @ConfigurationProperties("my.aliyun.sms")
    public static class SmsProperties {
        private String smsSignName;
        private String smsTemplateCode;
    }

}