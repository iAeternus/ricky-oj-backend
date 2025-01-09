package org.ricky.core.judger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ricky.core.judger.alter.command.JudgeCommand;
import org.ricky.core.judger.alter.response.JudgeResponse;
import org.ricky.core.judger.fetch.JudgerFetchService;
import org.ricky.core.judger.fetch.response.FetchJudgerInfoResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/7
 * @className JudgerController
 * @desc
 */
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "评测机相关接口")
@RequestMapping(value = "/judger")
public class JudgerController {

    private final JudgerFetchService judgerFetchService;

    @PostMapping("/judge")
    @Operation(summary = "评测")
    public JudgeResponse judge(@RequestBody @Valid JudgeCommand command) {
        return null;
    }

    @GetMapping("/version")
    @Operation(summary = "获取评测机版本以及其他信息")
    public FetchJudgerInfoResponse fetchJudgerInfo() {
        return judgerFetchService.fetchJudgerInfo();
    }

}
