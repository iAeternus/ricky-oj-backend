package org.ricky.core.submit;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ricky.core.submit.alter.SubmitAlterationService;
import org.ricky.core.submit.alter.dto.command.SubmitCommand;
import org.ricky.core.submit.alter.dto.response.SubmitResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/6
 * @className SubmitController
 * @desc
 */
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "提交评测相关接口")
@RequestMapping(value = "/submit")
public class SubmitController {

    private final SubmitAlterationService submitAlterationService;

    @PostMapping
    @Operation(summary = "提交评测")
    public SubmitResponse submit(@RequestBody @Valid SubmitCommand command) {
        return null;
    }

}
