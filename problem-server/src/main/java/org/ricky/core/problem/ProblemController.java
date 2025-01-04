package org.ricky.core.problem;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.validation.id.Id;
import org.ricky.core.problem.alter.ProblemAlterationService;
import org.ricky.core.problem.alter.dto.command.CreateProblemCommand;
import org.ricky.core.problem.alter.dto.command.UpdateProblemSettingCommand;
import org.ricky.core.problem.alter.dto.response.CreateProblemResponse;
import org.ricky.core.problem.alter.dto.response.UpdateProblemResponse;
import org.ricky.core.problem.fetch.ProblemFetchService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.ricky.common.constants.CommonConstants.PROBLEM_ID_PREFIX;

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

    @PutMapping("/{problemId}/setting")
    @Operation(summary = "更新设置")
    public UpdateProblemResponse updateProblemSetting(@PathVariable("problemId") @Id(prefix = PROBLEM_ID_PREFIX) String problemId,
                                                      @RequestBody @Valid UpdateProblemSettingCommand command,
                                                      @AuthenticationPrincipal UserContext userContext) {
        String version = problemAlterationService.updateProblemSetting(problemId, command, userContext);
        return UpdateProblemResponse.builder()
                .version(version)
                .build();
    }

    // TODO 更新题目标签，删除题目，修改题目，各种查询...

}
