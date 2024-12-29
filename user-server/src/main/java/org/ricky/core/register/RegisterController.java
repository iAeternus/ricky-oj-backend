package org.ricky.core.register;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ricky.core.register.alter.RegisterAlterService;
import org.ricky.core.register.alter.dto.command.RegisterCommand;
import org.ricky.core.register.alter.dto.response.RegisterResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/9/23
 * @className RegisterController
 * @desc
 */
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "注册相关接口")
@RequestMapping(value = "/registration")
public class RegisterController {

    private final RegisterAlterService registerAlterService;

    @PostMapping
    @Operation(summary = "注册")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@RequestBody @Valid RegisterCommand command) {
        return registerAlterService.register(command);
    }

}
