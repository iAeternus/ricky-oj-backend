package org.ricky.core.problem.domain.task;

import lombok.extern.slf4j.Slf4j;
import org.ricky.common.domain.task.RepeatableTask;
import org.ricky.core.problem.domain.casegroup.CaseGroupInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/16
 * @className AddCaseGroupTask
 * @desc
 */
@Slf4j
@Component
public class AddCaseGroupTask implements RepeatableTask {

    public void run(String problemId, List<String> caseGroupIds) {
        // 读取配置文件，写入文件系统
    }
}
