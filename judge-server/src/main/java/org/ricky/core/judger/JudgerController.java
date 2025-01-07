package org.ricky.core.judger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ricky.core.judger.fetch.JudgerFetchService;
import org.ricky.core.judger.fetch.dto.response.FetchJudgerInfoResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/version")
    @Operation(summary = "获取评测机版本以及其他信息")
    public FetchJudgerInfoResponse fetchJudgerInfo() {
        return judgerFetchService.fetchJudgerInfo();
    }

}
