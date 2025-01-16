package org.ricky.core.verification.alter.command;

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
 * @date 2024/9/25
 * @className CreateLoginVerificationCodeCommand
 * @desc 为登录生成验证码
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateLoginVerificationCodeCommand implements Command {

    @NotBlank
    @MobileOrEmail
    String mobileOrEmail;

}
