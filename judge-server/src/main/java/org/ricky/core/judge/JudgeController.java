package org.ricky.core.judge;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.validation.id.Id;
import org.ricky.core.judge.alter.JudgeAlterationService;
import org.ricky.core.judge.alter.command.ModifyStatusCommand;
import org.ricky.core.judge.alter.command.SubmitCommand;
import org.ricky.core.judge.alter.response.SubmitResponse;
import org.ricky.core.judge.fetch.JudgeFetchService;
import org.ricky.core.judge.fetch.response.FetchJudgeByIdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.ricky.common.constants.CommonConstants.JUDGE_ID_PREFIX;

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
    private final JudgeFetchService judgeFetchService;

    @PostMapping("/submit")
    @Operation(summary = "提交")
    @ResponseStatus(HttpStatus.CREATED)
    public SubmitResponse submit(@RequestBody @Valid SubmitCommand command,
                                 @AuthenticationPrincipal UserContext userContext) {
        return judgeAlterationService.submit(command, userContext);
    }

    @PutMapping("/modify/status")
    @Operation(summary = "变更评测状态")
    public void modifyStatus(@RequestBody @Valid ModifyStatusCommand command,
                             @AuthenticationPrincipal UserContext userContext) {
        judgeAlterationService.modifyStatus(command, userContext);
    }

    @GetMapping("/{judgeId}")
    @Operation(summary = "根据ID获取评测信息")
    public FetchJudgeByIdResponse fetchJudgeById(@PathVariable("judgeId") @Id(prefix = JUDGE_ID_PREFIX) String judgeId,
                                                 @AuthenticationPrincipal UserContext userContext) {
        return judgeFetchService.fetchJudgeById(judgeId, userContext);
    }

}
