package org.ricky.common.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.ricky.common.utils.CollectionUtils.listEquals;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/5
 * @className CollectionUtilsTest
 * @desc
 */
class CollectionUtilsTest {

    @Test
    void should_judge_list_equals() {
        // Given
        List<Integer> list1 = List.of(1, 2, 3, 4, 5);
        List<Integer> list2 = List.of(1, 2, 3, 4, 5);
        List<Integer> list3 = List.of();
        List<Integer> list4 = List.of();
        List<Integer> list5 = null;
        List<Integer> list6 = null;
        List<Integer> list7 = List.of(1, 2, 3, 4);

        // When
        boolean res = listEquals(list1, list2);
        boolean res2 = listEquals(list1, list3);
        boolean res3 = listEquals(list3, list4);
        boolean res4 = listEquals(list4, list5);
        boolean res5 = listEquals(list5, list6);
        boolean res6 = listEquals(list1, list7);

        // Then
        assertTrue(res);
        assertFalse(res2);
        assertTrue(res3);
        assertFalse(res4);
        assertTrue(res5);
        assertFalse(res6);
    }

}