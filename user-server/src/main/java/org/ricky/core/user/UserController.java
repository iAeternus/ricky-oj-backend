package org.ricky.core.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.core.user.alter.UserAlterationService;
import org.ricky.core.user.alter.dto.command.RegisterCommand;
import org.ricky.core.user.alter.dto.response.RegisterResponse;
import org.ricky.core.user.fetch.UserFetchService;
import org.ricky.core.user.fetch.dto.response.UserInfoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/28
 * @className UserController
 * @desc
 */
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "用户相关接口")
@RequestMapping(value = "/user")
public class UserController {

    private final UserAlterationService userAlterationService;
    private final UserFetchService userFetchService;

    @PostMapping("/registration")
    @Operation(summary = "注册")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@RequestBody @Valid RegisterCommand command) {
        return userAlterationService.register(command);
    }

    @GetMapping("/my/info")
    @Operation(summary = "获取我的用户信息")
    public UserInfoResponse fetchMyUserInfo(@AuthenticationPrincipal UserContext userContext) {
        return userFetchService.fetchMyUserInfo(userContext);
    }

}
