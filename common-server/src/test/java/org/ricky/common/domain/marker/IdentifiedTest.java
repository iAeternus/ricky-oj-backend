package org.ricky.common.domain.marker;

import org.junit.jupiter.api.Test;
import org.ricky.BaseUnitTest;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.ricky.common.domain.marker.Identified.isDuplicated;
import static org.ricky.common.utils.UUIDGenerator.newShortUUID;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/4
 * @className IdentifiedTest
 * @desc
 */
class IdentifiedTest extends BaseUnitTest {

    @Test
    void should_check_duplication() {
        String id = newShortUUID();
        FakeIdentified entity1 = new FakeIdentified(id);
        FakeIdentified entity2 = new FakeIdentified(id);
        assertTrue(isDuplicated(newHashSet(entity1, entity2)));
    }

    @Test
    void should_check_no_duplication() {
        FakeIdentified entity1 = new FakeIdentified(newShortUUID());
        FakeIdentified entity2 = new FakeIdentified(newShortUUID());
        assertFalse(isDuplicated(newHashSet(entity1, entity2)));
    }

    @Test
    void should_check_no_duplication_given_empty() {
        assertFalse(isDuplicated(newHashSet()));
        assertFalse(isDuplicated(null));
    }

    static class FakeIdentified implements Identified {
        private final String id;

        public FakeIdentified(String id) {
            this.id = id;
        }

        @Override
        public String getIdentifier() {
            return id;
        }
    }

}