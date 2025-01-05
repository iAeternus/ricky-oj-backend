package org.ricky.apiTest.core.tag;

import org.junit.jupiter.api.Test;
import org.ricky.apiTest.BaseApiTest;
import org.ricky.common.domain.LoginResponse;
import org.ricky.core.tag.alter.dto.command.CreateProblemTagCommand;
import org.ricky.core.tag.alter.dto.response.CreateProblemTagResponse;
import org.ricky.core.tag.domain.Tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ricky.apiTest.utils.ProblemRandomTestFixture.rCreateProblemTagCommand;
import static org.ricky.common.exception.ErrorCodeEnum.TAG_WITH_NAME_ALREADY_EXISTS;
import static org.ricky.common.utils.RandomTestFixture.rSentence;

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

}
