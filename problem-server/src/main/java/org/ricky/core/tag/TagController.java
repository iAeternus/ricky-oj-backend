package org.ricky.core.tag;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.core.tag.alter.TagAlterationService;
import org.ricky.core.tag.alter.dto.command.CreateProblemTagCommand;
import org.ricky.core.tag.alter.dto.response.CreateProblemTagResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/5
 * @className TagController
 * @desc
 */
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "题目标签相关接口")
@RequestMapping(value = "/tag")
public class TagController {

    private final TagAlterationService tagAlterationService;

    @PostMapping("/create")
    @Operation(summary = "创建题目标签")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateProblemTagResponse createProblemTag(@RequestBody @Valid CreateProblemTagCommand command,
                                                     @AuthenticationPrincipal UserContext userContext) {
        return tagAlterationService.createProblemTag(command, userContext);
    }

}
