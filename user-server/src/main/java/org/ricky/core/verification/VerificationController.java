package org.ricky.core.verification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ricky.common.validation.id.Id;
import org.ricky.core.common.domain.ReturnId;
import org.ricky.core.verification.alter.VerificationCodeAlterationService;
import org.ricky.core.verification.alter.command.CreateLoginVerificationCodeCommand;
import org.ricky.core.verification.alter.command.CreateRegisterVerificationCodeCommand;
import org.ricky.core.verification.fetch.VerificationCodeFetchService;
import org.ricky.core.verification.fetch.response.FetchVerificationByIdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.ricky.common.constants.CommonConstants.VERIFICATION_ID_PREFIX;

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
@RequestMapping(value = "/verification-code")
public class VerificationController {

    private final VerificationCodeAlterationService verificationCodeAlterationService;
    private final VerificationCodeFetchService verificationCodeFetchService;

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "为注册生成验证码")
    @PostMapping(value = "/for-register")
    public ReturnId createVerificationCodeForRegister(@RequestBody @Valid CreateRegisterVerificationCodeCommand command) {
        String verificationCodeId = verificationCodeAlterationService.createVerificationCodeForRegister(command);
        return ReturnId.returnId(verificationCodeId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "为登录生成验证码")
    @PostMapping(value = "/for-login")
    public ReturnId createVerificationCodeForLogin(@RequestBody @Valid CreateLoginVerificationCodeCommand command) {
        String verificationCodeId = verificationCodeAlterationService.createVerificationCodeForLogin(command);
        return ReturnId.returnId(verificationCodeId);
    }

    @Operation(summary = "根据ID获取验证码")
    @GetMapping("/fetch/{verificationCodeId}")
    public FetchVerificationByIdResponse fetchById(@PathVariable("verificationCodeId")
                                                   @Id(prefix = VERIFICATION_ID_PREFIX) String verificationCodeId) {
        return verificationCodeFetchService.fetchById(verificationCodeId);
    }

}
