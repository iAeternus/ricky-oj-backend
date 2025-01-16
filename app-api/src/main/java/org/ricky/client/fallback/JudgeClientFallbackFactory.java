package org.ricky.client.fallback;

import lombok.extern.slf4j.Slf4j;
import org.ricky.client.JudgeClient;
import org.ricky.dto.alter.command.ModifyJudgeResultCommand;
import org.ricky.dto.alter.command.ModifyStatusCommand;
import org.ricky.dto.alter.response.Judge;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/9
 * @className JudgeClientFallbackFactory
 * @desc
 */
@Slf4j
public class JudgeClientFallbackFactory implements FallbackFactory<JudgeClient> {
    @Override
    public JudgeClient create(Throwable cause) {
        return new JudgeClient() {
            @Override
            public void modifyStatus(ModifyStatusCommand command) {
                log.error("修改评测状态失败, judgeId[{}]", command.getJudgeId());
            }

            @Override
            public void modifyJudgeResult(ModifyJudgeResultCommand command) {
                log.error("修改评测结果失败, judgeId[{}]", command.getJudgeId());
            }

            @Override
            public Judge fetchJudgeById(String judgeId) {
                log.error("根据ID查询评测信息失败, judgeId[{}]", judgeId);
                return null;
            }
        };
    }
}
