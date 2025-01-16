package org.ricky.core.problem.domain.task;

import lombok.extern.slf4j.Slf4j;
import org.ricky.common.domain.task.RepeatableTask;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/16
 * @className AddCaseGroupFailedTask
 * @desc
 */
@Slf4j
@Component
public class AddCaseGroupFailedTask implements RepeatableTask {


    public void run(String problemId, List<String> caseGroupIds) {
        // 查找文件夹中是否有错误的文件，删除掉
    }
}
