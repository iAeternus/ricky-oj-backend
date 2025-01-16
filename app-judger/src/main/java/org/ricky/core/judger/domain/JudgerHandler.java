package org.ricky.core.judger.domain;

import org.ricky.core.judger.domain.context.JudgeResult;
import org.ricky.dto.alter.response.Judge;
import org.ricky.dto.fetch.response.ProblemSetting;
import org.springframework.stereotype.Component;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/13
 * @className JudgerHandler
 * @desc 编译，运行，评判处理器
 */
@Component
public class JudgerHandler {

    public JudgeResult judge(ProblemSetting problemSetting, Judge judge) {
        return null;
    }

}
