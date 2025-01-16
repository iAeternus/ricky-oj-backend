package org.ricky.apiTest.utils;

import org.ricky.common.utils.RandomTestFixture;
import org.ricky.core.judge.alter.command.SubmitCommand;

import static org.ricky.common.constants.CommonConstants.PROBLEM_ID_PREFIX;
import static org.ricky.common.constants.CommonConstants.SUBMIT_TOKEN;
import static org.ricky.management.SystemManager.newId;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/7
 * @className JudgerRandomTextFixture
 * @desc
 */
public class JudgeRandomTextFixture extends RandomTestFixture {

    public static SubmitCommand rSubmitCommand() {
        return SubmitCommand.builder()
                .token(SUBMIT_TOKEN)
                .problemId(newId(PROBLEM_ID_PREFIX))
                .share(rBool())
                .submitType(rShort(0, 2))
                .isRemote(rBool())
                .program(rProgram())
                .build();
    }

}
