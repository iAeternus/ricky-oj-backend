package org.ricky.core.problem;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.core.problem.alter.ProblemAlterationService;
import org.ricky.core.problem.alter.dto.command.CreateProblemCommand;
import org.ricky.core.problem.alter.dto.response.CreateProblemResponse;
import org.ricky.core.problem.fetch.ProblemFetchService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/26
 * @className ProblemController
 * @desc
 */
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "题目相关接口")
@RequestMapping(value = "/problem")
public class ProblemController {

    private final ProblemAlterationService problemAlterationService;
    private final ProblemFetchService problemFetchService;

    @PostMapping("/create")
    @Operation(summary = "创建题目")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateProblemResponse createProblem(@RequestBody @Valid CreateProblemCommand command,
                                               @AuthenticationPrincipal UserContext userContext) {
        return problemAlterationService.createProblem(command, userContext);
    }

}
