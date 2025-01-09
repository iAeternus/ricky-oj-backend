package org.ricky.core.user.alter.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Command;
import org.ricky.common.exception.MyException;
import org.ricky.common.validation.mobileoremail.MobileOrEmail;
import org.ricky.common.validation.password.Password;
import org.ricky.common.validation.verficationcode.VerificationCode;

import static org.ricky.common.constants.CommonConstants.MAX_GENERIC_NAME_LENGTH;
import static org.ricky.common.exception.ErrorCodeEnum.MUST_SIGN_AGREEMENT;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/30
 * @className RegisterCommand
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterCommand implements Command {

    @NotBlank
    @Schema(name = "昵称")
    @Size(max = MAX_GENERIC_NAME_LENGTH)
    String nickname;

    @NotBlank
    @Password
    @Schema(name = "密码")
    String password;

    @NotBlank
    @MobileOrEmail
    @Schema(name = "手机号或邮箱")
    String mobileOrEmail;

    @NotBlank
    @VerificationCode
    @Schema(name = "验证码")
    String verification;

    @Schema(name = "是否同意用户协议")
    boolean agreement;

    @Override
    public void correctAndValidate() {
        if (!agreement) {
            throw new MyException(MUST_SIGN_AGREEMENT, "请同意用户协议以完成注册。");
        }
    }

}
