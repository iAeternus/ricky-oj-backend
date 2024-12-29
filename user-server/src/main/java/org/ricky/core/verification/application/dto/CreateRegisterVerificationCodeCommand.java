package org.ricky.core.verification.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Command;
import org.ricky.common.validation.mobileoremail.MobileOrEmail;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/9/24
 * @className CreateRegisterVerificationCodeCommand
 * @desc 为注册生成验证码
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateRegisterVerificationCodeCommand implements Command {

    @NotBlank
    @MobileOrEmail
    String mobileOrEmail;

}
