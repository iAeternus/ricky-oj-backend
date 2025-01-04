package org.ricky.core.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ricky.core.user.alter.UserAlterationService;
import org.ricky.core.user.alter.dto.command.RegisterCommand;
import org.ricky.core.user.alter.dto.response.RegisterResponse;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/registration")
    @Operation(summary = "注册")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@RequestBody @Valid RegisterCommand command) {
        return userAlterationService.register(command);
    }

}
