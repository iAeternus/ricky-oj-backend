package org.ricky.common.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.ricky.common.utils.UUIDGenerator.newShortUUID;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/4
 * @className UUIDGeneratorTest
 * @desc
 */
class UUIDGeneratorTest {

    @Test
    void should_generate_random_base64_uuid() {
        String uuid = newShortUUID();
        assertEquals(22, uuid.length());
    }

}