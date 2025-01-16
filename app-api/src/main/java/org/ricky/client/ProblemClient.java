package org.ricky.client;

import org.ricky.client.fallback.ProblemClientFallbackFactory;
import org.ricky.common.validation.id.Id;
import org.ricky.config.OpenFeignConfiguration;
import org.ricky.dto.fetch.response.ProblemInfo;
import org.ricky.dto.fetch.response.ProblemSetting;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.ricky.common.constants.CommonConstants.PROBLEM_ID_PREFIX;


/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/7
 * @className ProblemClient
 * @desc
 */
@FeignClient(
        value = "problem-server",
        contextId = "problemClient",
        fallback = ProblemClientFallbackFactory.class,
        configuration = OpenFeignConfiguration.class
)
public interface ProblemClient {

    String ROOT_URL = "/problem";

    @GetMapping(ROOT_URL + "/{problemId}")
    ProblemInfo fetchById(@PathVariable("problemId") @Id(prefix = PROBLEM_ID_PREFIX) String problemId);

    @GetMapping(ROOT_URL + "/{problemId}/setting")
    ProblemSetting fetchSettingById(@PathVariable("problemId") @Id(prefix = PROBLEM_ID_PREFIX) String problemId);

}
