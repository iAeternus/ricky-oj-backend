package org.ricky.apiTest.core.judge;

import org.junit.jupiter.api.Test;
import org.ricky.apiTest.BaseApiTest;
import org.ricky.common.domain.LoginResponse;
import org.ricky.core.judge.alter.command.SubmitCommand;
import org.ricky.core.judge.alter.response.SubmitResponse;
import org.ricky.core.judge.domain.Judge;
import org.ricky.core.judge.domain.event.SubmittedEvent;

import static org.junit.jupiter.api.Assertions.*;
import static org.ricky.apiTest.utils.JudgeRandomTextFixture.rSubmitCommand;
import static org.ricky.common.domain.event.DomainEventTypeEnum.SUBMITTED;
import static org.ricky.core.judge.domain.JudgeStatusEnum.PENDING;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/9
 * @className JudgeControllerApiTest
 * @desc 这个测试类无法使用，因为spring启动类重复
 */
public class JudgeControllerApiTest extends BaseApiTest {

    @Test
    public void should_submit() {
        // Given
        LoginResponse operator = setupApi.registerWithLogin();
        SubmitCommand command = rSubmitCommand();

        // When
        SubmitResponse response = JudgeApi.submit(operator.getJwt(), command);

        // Then
        Judge judge = judgeRepository.byId(response.getJudgeId());
        assertNotNull(judge.getSubmit());
        assertEquals(PENDING, judge.getStatus());
        assertNull(judge.getResult());
    }

    @Test
    public void should_raise_submitted_event_after_submit() {
        // Given
        LoginResponse operator = setupApi.registerWithLogin();
        SubmitCommand command = rSubmitCommand();

        // When
        SubmitResponse response = JudgeApi.submit(operator.getJwt(), command);

        // Then
        SubmittedEvent theEvent = domainEventDao.latestEventFor(response.getJudgeId(), SUBMITTED, SubmittedEvent.class);
        assertEquals(response.getJudgeId(), theEvent.getJudgeId());
    }

}
