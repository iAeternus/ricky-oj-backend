package org.ricky.core.judge;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.core.judge.alter.JudgeAlterationService;
import org.ricky.core.judge.alter.command.SubmitCommand;
import org.ricky.core.judge.alter.response.SubmitResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/6
 * @className JudgeController
 * @desc
 */
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "评测相关接口")
@RequestMapping(value = "/judge")
public class JudgeController {

    private final JudgeAlterationService judgeAlterationService;

    @PostMapping
    @Operation(summary = "提交")
    @ResponseStatus(HttpStatus.CREATED)
    public SubmitResponse submit(@RequestBody @Valid SubmitCommand command,
                                 @AuthenticationPrincipal UserContext userContext) {
        return judgeAlterationService.submit(command, userContext);
    }

}
