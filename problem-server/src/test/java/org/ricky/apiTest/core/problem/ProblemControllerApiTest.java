package org.ricky.apiTest.core.problem;

import org.junit.jupiter.api.Test;
import org.ricky.apiTest.BaseApiTest;
import org.ricky.apiTest.core.tag.TagApi;
import org.ricky.common.domain.LoginResponse;
import org.ricky.common.exception.MyException;
import org.ricky.core.problem.alter.command.CreateProblemCommand;
import org.ricky.core.problem.alter.command.UpdateProblemSettingCommand;
import org.ricky.core.problem.alter.command.UpdateProblemTagsCommand;
import org.ricky.core.problem.alter.response.CreateProblemResponse;
import org.ricky.core.problem.alter.response.UpdateProblemResponse;
import org.ricky.core.problem.alter.response.UpdateProblemTagsResponse;
import org.ricky.core.problem.domain.Problem;
import org.ricky.core.problem.domain.event.ProblemDeletedEvent;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.ricky.apiTest.utils.ProblemRandomTestFixture.*;
import static org.ricky.common.constants.CommonConstants.*;
import static org.ricky.common.domain.event.DomainEventTypeEnum.PROBLEM_DELETED;
import static org.ricky.common.exception.ErrorCodeEnum.AR_NOT_FOUND;
import static org.ricky.common.exception.ErrorCodeEnum.PROBLEM_WITH_CUSTOM_ID_ALREADY_EXISTS;
import static org.ricky.common.utils.ValidationUtils.isEmpty;
import static org.ricky.core.problem.domain.setting.ProblemSetting.defaultProblemSetting;
import static org.ricky.management.SystemManager.newId;

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

    @Test
    public void should_update_problem_tags() {
        // Given
        LoginResponse operator = setupApi.registerWithLogin();
        String problemId = ProblemApi.createProblem(operator.getJwt(), rCreateProblemCommand()).getProblemId();
        String tagId = TagApi.createProblemTag(operator.getJwt(), rCreateProblemTagCommand()).getTagId();
        UpdateProblemTagsCommand command = UpdateProblemTagsCommand.builder()
                .tags(List.of(tagId))
                .build();

        // When
        UpdateProblemTagsResponse response = ProblemApi.updateProblemTags(operator.getJwt(), problemId, command);

        // Then
        assertEquals(List.of(tagId), response.getAdded());
        assertTrue(isEmpty(response.getDeleted()));

        // When
        UpdateProblemTagsResponse response2 = ProblemApi.updateProblemTags(operator.getJwt(), problemId,
                UpdateProblemTagsCommand.builder().tags(List.of()).build());

        // Then
        assertTrue(isEmpty(response2.getAdded()));
        assertEquals(List.of(tagId), response2.getDeleted());
    }

    @Test
    public void should_update_problem_tags_if_no_change() {
        // Given
        LoginResponse operator = setupApi.registerWithLogin();
        String problemId = ProblemApi.createProblem(operator.getJwt(), rCreateProblemCommand()).getProblemId();
        String tagId = TagApi.createProblemTag(operator.getJwt(), rCreateProblemTagCommand()).getTagId();
        UpdateProblemTagsCommand command = UpdateProblemTagsCommand.builder()
                .tags(List.of(tagId))
                .build();
        ProblemApi.updateProblemTags(operator.getJwt(), problemId, command); // 先更新一次

        // When
        UpdateProblemTagsResponse response = ProblemApi.updateProblemTags(operator.getJwt(), problemId, command);

        // Then
        assertTrue(isEmpty(response.getAdded()));
        assertTrue(isEmpty(response.getDeleted()));
    }

    @Test
    public void should_remove_problem() {
        // Given
        LoginResponse operator = setupApi.registerWithLogin();
        String problemId = ProblemApi.createProblem(operator.getJwt(), rCreateProblemCommand()).getProblemId();

        // When
        ProblemApi.removeProblem(operator.getJwt(), problemId);

        // Then
        assertThrows(MyException.class, () -> problemRepository.byId(problemId));
    }

    @Test
    public void should_fail_to_remove_if_problem_not_exists() {
        // Given
        LoginResponse operator = setupApi.registerWithLogin();

        // When & Then
        assertError(() -> ProblemApi.removeProblemRaw(operator.getJwt(), newId(PROBLEM_ID_PREFIX)), AR_NOT_FOUND);
    }

    @Test
    public void should_raise_problem_deleted_event_after_remove() {
        // Given
        LoginResponse operator = setupApi.registerWithLogin();
        String problemId = ProblemApi.createProblem(operator.getJwt(), rCreateProblemCommand()).getProblemId();

        // When
        ProblemApi.removeProblem(operator.getJwt(), problemId);

        // Then
        ProblemDeletedEvent theEvent = domainEventDao.latestEventFor(problemId, PROBLEM_DELETED, ProblemDeletedEvent.class);
        assertEquals(problemId, theEvent.getProblemId());
    }

}
