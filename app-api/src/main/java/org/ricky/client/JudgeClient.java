package org.ricky.client;

import jakarta.validation.Valid;
import org.ricky.client.fallback.JudgeClientFallbackFactory;
import org.ricky.common.validation.id.Id;
import org.ricky.config.OpenFeignConfiguration;
import org.ricky.dto.alter.command.ModifyJudgeResultCommand;
import org.ricky.dto.alter.command.ModifyStatusCommand;
import org.ricky.dto.alter.response.Judge;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.ricky.common.constants.CommonConstants.JUDGE_ID_PREFIX;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/9
 * @className JudgeClient
 * @desc
 */
@FeignClient(
        value = "judge-server",
        contextId = "judgeClient",
        fallback = JudgeClientFallbackFactory.class,
        configuration = OpenFeignConfiguration.class
)
public interface JudgeClient {

    String ROOT_URL = "/judge";

    @PutMapping(ROOT_URL + "/modify/status")
    void modifyStatus(@RequestBody @Valid ModifyStatusCommand command);

    @PutMapping(ROOT_URL + "/modify/result")
    void modifyJudgeResult(@RequestBody @Valid ModifyJudgeResultCommand command);

    @GetMapping(ROOT_URL + "/{judgeId}")
    Judge fetchJudgeById(@PathVariable("judgeId") @Id(prefix = JUDGE_ID_PREFIX) String judgeId);

}
