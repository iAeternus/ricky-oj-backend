package org.ricky.apiTest.core.problem;

import org.junit.jupiter.api.Test;
import org.ricky.apiTest.BaseApiTest;
import org.ricky.common.domain.LoginResponse;
import org.ricky.core.problem.alter.dto.command.CreateProblemCommand;
import org.ricky.core.problem.alter.dto.command.UpdateProblemSettingCommand;
import org.ricky.core.problem.alter.dto.response.CreateProblemResponse;
import org.ricky.core.problem.alter.dto.response.UpdateProblemResponse;
import org.ricky.core.problem.domain.Problem;
import org.ricky.core.problem.domain.setting.ProblemSetting;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.ricky.apiTest.utils.ProblemRandomTestFixture.rCreateProblemCommand;
import static org.ricky.apiTest.utils.ProblemRandomTestFixture.rProblemSetting;
import static org.ricky.common.constants.CommonConstants.MAX_GENERIC_NAME_LENGTH;
import static org.ricky.common.constants.CommonConstants.MAX_GENERIC_TEXT_LENGTH;
import static org.ricky.common.exception.ErrorCodeEnum.PROBLEM_WITH_CUSTOM_ID_ALREADY_EXISTS;
import static org.ricky.common.utils.RandomTestFixture.*;
import static org.ricky.core.problem.domain.setting.ProblemSetting.defaultProblemSetting;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/4
 * @className ProblemControllerApiTest
 * @desc
 */
public class ProblemControllerApiTest extends BaseApiTest {

    @Test
    public void should_create_problem() {
        // Given
        LoginResponse operator = setupApi.registerWithLogin();
        CreateProblemCommand command = CreateProblemCommand.builder()
                .customId(rCustomId())
                .title(rSentence(MAX_GENERIC_NAME_LENGTH))
                .author(rNickname())
                .description(rSentence(MAX_GENERIC_TEXT_LENGTH))
                .inputFormat(rSentence(MAX_GENERIC_TEXT_LENGTH))
                .outputFormat(rSentence(MAX_GENERIC_TEXT_LENGTH))
                .inputCases(List.of(rInputCase(2)))
                .outputCases(List.of(rOutputCase(2)))
                .hint(rSentence(MAX_GENERIC_TEXT_LENGTH))
                .build();

        // When
        CreateProblemResponse response = ProblemApi.createProblem(operator.getJwt(), command);

        // Then
        Problem problem = problemRepository.byId(response.getProblemId());
        assertEquals(problem.getSetting(), defaultProblemSetting());
    }

    @Test
    public void should_fail_to_create_if_custom_id_duplicated() {
        // Given
        LoginResponse operator = setupApi.registerWithLogin();
        String customId = rCustomId();
        CreateProblemCommand command = rCreateProblemCommand(customId);
        ProblemApi.createProblem(operator.getJwt(), command); // 先创建抢占自定义ID

        // When & Then
        assertError(() -> ProblemApi.createProblemRaw(operator.getJwt(), command), PROBLEM_WITH_CUSTOM_ID_ALREADY_EXISTS);
    }

    @Test
    public void should_update_problem_setting() {
        // Given
        LoginResponse operator = setupApi.registerWithLogin();
        String problemId = ProblemApi.createProblem(operator.getJwt(), rCreateProblemCommand()).getProblemId();
        String oldVersion = problemRepository.byId(problemId).getVersion();
        UpdateProblemSettingCommand command = UpdateProblemSettingCommand.builder()
                .setting(rProblemSetting())
                .version(oldVersion)
                .build();

        // When
        UpdateProblemResponse response = ProblemApi.updateProblemSetting(operator.getJwt(), problemId, command);

        // Then
        assertNotEquals(oldVersion, response.getVersion());
    }

    @Test
    public void should_update_problem_setting_if_no_change() {
        // Given
        LoginResponse operator = setupApi.registerWithLogin();
        String problemId = ProblemApi.createProblem(operator.getJwt(), rCreateProblemCommand()).getProblemId();
        Problem problem = problemRepository.byId(problemId);
        UpdateProblemSettingCommand command = UpdateProblemSettingCommand.builder()
                .setting(problem.getSetting())
                .version(problem.getVersion())
                .build();

        // When
        UpdateProblemResponse response = ProblemApi.updateProblemSetting(operator.getJwt(), problemId, command);

        // Then
        assertEquals(problem.getVersion(), response.getVersion());
    }

}
