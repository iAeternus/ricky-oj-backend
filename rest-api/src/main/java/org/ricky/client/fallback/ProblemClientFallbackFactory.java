package org.ricky.client.fallback;

import lombok.extern.slf4j.Slf4j;
import org.ricky.client.ProblemClient;
import org.ricky.dto.fetch.response.FetchProblemByIdResponse;
import org.ricky.dto.fetch.response.FetchSettingByIdResponse;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/7
 * @className ProblemClientFallbackFactory
 * @desc
 */
@Slf4j
public class ProblemClientFallbackFactory implements FallbackFactory<ProblemClient> {
    @Override
    public ProblemClient create(Throwable cause) {
        return new ProblemClient() {
            @Override
            public FetchProblemByIdResponse fetchById(String problemId) {
                log.error("查询题目失败, problem[{}]", problemId);
                return null;
            }

            @Override
            public FetchSettingByIdResponse fetchSettingById(String problemId) {
                log.error("查询题目设置失败, problem[{}]", problemId);
                return null;
            }
        };
    }
}