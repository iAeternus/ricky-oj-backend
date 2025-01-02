package org.ricky.core.login.alter.dto.command;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Command;
import org.ricky.common.validation.mobileoremail.MobileOrEmail;
import org.ricky.common.validation.verficationcode.VerificationCode;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/30
 * @className VerificationCodeLoginCommand
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VerificationCodeLoginCommand implements Command {

    @NotBlank
    @MobileOrEmail
    String mobileOrEmail;

    @NotBlank
    @VerificationCode
    String verification;

}
