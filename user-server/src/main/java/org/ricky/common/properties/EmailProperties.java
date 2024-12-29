package org.ricky.common.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/29
 * @className EmailProperties
 * @desc
 */
@Data
@Component
@Validated
@ConfigurationProperties("my.email")
public class EmailProperties {

    /**
     * 寄信方用户名
     */
    @NotBlank
    String fromName;

    /**
     * 寄信方邮箱
     */
    @NotBlank
    String fromEmail;

}
