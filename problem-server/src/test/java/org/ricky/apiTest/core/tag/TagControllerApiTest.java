package org.ricky.apiTest.core.tag;

import org.junit.jupiter.api.Test;
import org.ricky.apiTest.BaseApiTest;
import org.ricky.apiTest.core.problem.ProblemApi;
import org.ricky.common.constants.CommonConstants;
import org.ricky.common.domain.LoginResponse;
import org.ricky.core.problem.alter.dto.command.UpdateProblemTagsCommand;
import org.ricky.core.problem.domain.Problem;
import org.ricky.core.tag.alter.dto.command.CreateProblemTagCommand;
import org.ricky.core.tag.alter.dto.command.UpdateTagInfoCommand;
import org.ricky.core.tag.alter.dto.response.CreateProblemTagResponse;
import org.ricky.core.tag.domain.Tag;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.ricky.apiTest.utils.ProblemRandomTestFixture.*;
import static org.ricky.common.constants.CommonConstants.GROUP_ID_PREFIX;
import static org.ricky.common.constants.CommonConstants.TAG_ID_PREFIX;
import static org.ricky.common.exception.ErrorCodeEnum.AR_NOT_FOUND;
import static org.ricky.common.exception.ErrorCodeEnum.TAG_WITH_NAME_ALREADY_EXISTS;
import static org.ricky.common.utils.RandomTestFixture.*;
import static org.ricky.management.SystemManager.newId;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/5
 * @className TagControllerApiTest
 * @desc
 */
public class TagControllerApiTest extends BaseApiTest {

    @Test
    public void should_create_tag() {
        // Given
        LoginResponse operator = setupApi.registerWithLogin();
        String name = rSentence(6);
        CreateProblemTagCommand command = rCreateProblemTagCommand(name);

        // When
        CreateProblemTagResponse response = TagApi.createProblemTag(operator.getJwt(), command);

        // Then
        assertNotNull(response.getTagId());
        Tag tag = tagRepository.byId(response.getTagId());
        assertEquals(name, tag.getName());
    }

    @Test
    public void should_fail_to_create_if_name_duplicated() {
        // Given
        LoginResponse operator = setupApi.registerWithLogin();
        String name = rSentence(6);
        CreateProblemTagCommand command = rCreateProblemTagCommand(name);
        TagApi.createProblemTag(operator.getJwt(), command); // 先创建以抢占name

        // When & Then
        assertError(() -> TagApi.createProblemTagRaw(operator.getJwt(), command), TAG_WITH_NAME_ALREADY_EXISTS);
    }

    @Test
    public void should_remove_problem_tag() {
        // Given
        LoginResponse operator = setupApi.registerWithLogin();
        String tagId = TagApi.createProblemTag(operator.getJwt(), rCreateProblemTagCommand()).getTagId();
        String problemId = ProblemApi.createProblem(operator.getJwt(), rCreateProblemCommand()).getProblemId();
        ProblemApi.updateProblemTags(operator.getJwt(), problemId, UpdateProblemTagsCommand.builder().tags(List.of(tagId)).build());

        // When
        TagApi.removeProblemTag(operator.getJwt(), tagId);

        // Then
        assertFalse(tagRepository.exists(tagId));
        Problem problem = problemRepository.byId(problemId); // 级联删除
        assertFalse(problem.getTags().contains(tagId));
    }

    @Test
    public void should_fail_to_remove_if_tag_not_exists() {
        // Given
        LoginResponse operator = setupApi.registerWithLogin();

        // When & Then
        assertError(() -> TagApi.removeProblemTagRaw(operator.getJwt(), newId(TAG_ID_PREFIX)), AR_NOT_FOUND);
    }

    @Test
    public void should_update_problem_tag() {
        // Given
        LoginResponse operator = setupApi.registerWithLogin();
        String tagId = TagApi.createProblemTag(operator.getJwt(), rCreateProblemTagCommand()).getTagId();
        String name = rSentence(6);
        UpdateTagInfoCommand command = rUpdateTagInfoCommand(name);

        // When
        TagApi.updateProblemTag(operator.getJwt(), tagId, command);

        // Then
        Tag tag = tagRepository.byId(tagId);
        assertEquals(name, tag.getName());
    }

    @Test
    public void should_fail_to_update_if_tag_not_exists() {
        // Given
        LoginResponse operator = setupApi.registerWithLogin();
        UpdateTagInfoCommand command = rUpdateTagInfoCommand();

        // When & Then
        assertError(() -> TagApi.updateProblemTagRaw(operator.getJwt(), newId(TAG_ID_PREFIX), command), AR_NOT_FOUND);
    }

}
