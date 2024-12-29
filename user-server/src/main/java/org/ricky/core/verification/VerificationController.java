package org.ricky.core.verification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ricky.core.common.domain.ReturnId;
import org.ricky.core.verification.application.VerificationCodeApplicationService;
import org.ricky.core.verification.application.dto.CreateLoginVerificationCodeCommand;
import org.ricky.core.verification.application.dto.CreateRegisterVerificationCodeCommand;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/9/23
 * @className VerificationController
 * @desc
 */
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "验证码相关接口")
@RequestMapping(value = "/verification-codes")
public class VerificationController {

    private final VerificationCodeApplicationService verificationCodeApplicationService;

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "为注册生成验证码")
    @PostMapping(value = "/for-register")
    public ReturnId createVerificationCodeForRegister(@RequestBody @Valid CreateRegisterVerificationCodeCommand command) {
        String verificationCodeId = verificationCodeApplicationService.createVerificationCodeForRegister(command);
        return ReturnId.returnId(verificationCodeId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "为登录生成验证码")
    @PostMapping(value = "/for-login")
    public ReturnId createVerificationCodeForLogin(@RequestBody @Valid CreateLoginVerificationCodeCommand command) {
        String verificationCodeId = verificationCodeApplicationService.createVerificationCodeForLogin(command);
        return ReturnId.returnId(verificationCodeId);
    }

}
