package org.ricky.common.domain;

import org.junit.jupiter.api.Test;
import org.ricky.BaseUnitTest;
import org.ricky.common.context.UserContext;
import org.ricky.common.domain.event.DomainEvent;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.ricky.common.constants.CommonConstants.USER_ID_PREFIX;
import static org.ricky.common.context.RoleEnum.SYSTEM_ADMIN;
import static org.ricky.common.context.UserContext.createUser;
import static org.ricky.common.domain.AggregateRoot.MAX_OPS_LOG_SIZE;
import static org.ricky.common.utils.SnowflakeIdGenerator.newSnowflakeId;
import static org.ricky.management.SystemManager.newId;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/4
 * @className AggregateRootTest
 * @desc
 */
class AggregateRootTest extends BaseUnitTest {

    private static final UserContext TEST_USER = createUser(newId(USER_ID_PREFIX), "username", SYSTEM_ADMIN);

    @Test
    public void should_create() {
        String id = "Test" + newSnowflakeId();

        TestAggregate aggregate = new TestAggregate(id);
        assertEquals(id, aggregate.getId());
        assertEquals(TEST_USER.getUserId(), aggregate.getUserId());

        assertEquals(TEST_USER.getUserId(), aggregate.getCreatedBy());
        assertNotNull(aggregate.getCreatedAt());

        assertNull(aggregate.getEvents());
        assertNull(aggregate.getOpsLogs());

        assertEquals(id, aggregate.getIdentifier());
    }

    @Test
    public void should_raise_event() {
        String id = "Test" + newSnowflakeId();
        TestAggregate aggregate = new TestAggregate(id);
        DomainEvent event = new DomainEvent() {
        };

        aggregate.raiseEvent(event);
        assertSame(event, aggregate.getEvents().get(0));
    }

    @Test
    public void should_clear_events() {
        String id = "Test" + newSnowflakeId();
        TestAggregate aggregate = new TestAggregate(id);
        DomainEvent event = new DomainEvent() {
        };
        aggregate.raiseEvent(event);
        aggregate.clearEvents();
        assertNull(aggregate.getEvents());
    }

    @Test
    public void should_add_ops_log() {
        String id = "Test" + newSnowflakeId();
        TestAggregate aggregate = new TestAggregate(id);
        String opsLog = "Hello ops logs";
        aggregate.addOpsLog(opsLog, TEST_USER);
        assertEquals(opsLog, aggregate.getOpsLogs().get(0).getNote());
    }

    @Test
    public void should_slice_ops_logs_if_too_much() {
        String id = "Test" + newSnowflakeId();
        TestAggregate aggregate = new TestAggregate(id);
        String opsLog = "Hello ops logs";
        IntStream.rangeClosed(0, MAX_OPS_LOG_SIZE).forEach(i -> aggregate.addOpsLog(opsLog, TEST_USER));
        String lastLog = "last ops log";
        aggregate.addOpsLog(lastLog, TEST_USER);
        assertEquals(MAX_OPS_LOG_SIZE, aggregate.getOpsLogs().size());
        assertEquals(lastLog, aggregate.getOpsLogs().get(MAX_OPS_LOG_SIZE - 1).getNote());
    }

    static class TestAggregate extends AggregateRoot {

        public TestAggregate(String id) {
            super(id, TEST_USER);
        }
    }

}