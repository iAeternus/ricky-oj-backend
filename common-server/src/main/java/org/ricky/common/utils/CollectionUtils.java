package org.ricky.common.utils;

import java.util.*;

import static org.ricky.common.utils.ValidationUtils.isNull;
import static org.ricky.common.utils.ValidationUtils.notEquals;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/26
 * @className CollectionUtils
 * @desc 集合工具类
 */
public class CollectionUtils {

    // List

    /**
     * 求两个列表的差集，list1 - list2<br>
     * 时间复杂度：O(n + m)，n为第一个列表的长度，m为第二个列表的长度<br>
     *
     * @param list1 第一个列表
     * @param list2 第二个列表
     * @return 差集
     */
    public static <T> Set<T> diff(List<T> list1, List<T> list2) {
        HashSet<T> result = new HashSet<>(list1);
        result.removeAll(new HashSet<>(list2));
        return result;
    }

    public static <T> String listToString(List<T> list, String begin, String end, String separator) {
        if (list == null || list.isEmpty()) {
            return begin + end;
        }

        StringBuilder stringBuilder = new StringBuilder(begin + list.get(0));
        for (int i = 1; i < list.size(); i++) {
            stringBuilder.append(separator).append(list.get(i));
        }
        stringBuilder.append(end);
        return stringBuilder.toString();
    }

    public static <T> String listToString(List<T> list) {
        return listToString(list, "[", "]", ",");
    }

    public static <T> int listSize(List<T> list) {
        return isNull(list) ? 0 : list.size();
    }

    public static <T> boolean listEquals(List<T> aList, List<T> bList) {
        if (isNull(aList) && isNull(bList)) {
            return true;
        }
        if (isNull(aList) || isNull(bList) || listSize(aList) != listSize(bList)) {
            return false;
        }
        int size = aList.size();
        for (int i = 0; i < size; ++i) {
            if (notEquals(aList.get(i), bList.get(i))) {
                return false;
            }
        }
        return true;
    }

    // Map

    public static <K, V> Map<K, V> mapOf(K key, V value) {
        HashMap<K, V> map = new HashMap<>(2, 0.9f);
        map.put(key, value);
        return map;
    }

    public static <K, V> Map<K, V> mapOf(K key1, V value1,
                                         K key2, V value2) {
        HashMap<K, V> map = new HashMap<>(3, 0.9f);
        map.put(key1, value1);
        map.put(key2, value2);
        return map;
    }

    public static <K, V> Map<K, V> mapOf(K key1, V value1,
                                         K key2, V value2,
                                         K key3, V value3) {
        HashMap<K, V> map = new HashMap<>(4, 0.9f);
        map.put(key1, value1);
        map.put(key2, value2);
        map.put(key3, value3);
        return map;
    }

    public static <K, V> Map<K, V> mapOf(K key1, V value1,
                                         K key2, V value2,
                                         K key3, V value3,
                                         K key4, V value4) {
        HashMap<K, V> map = new HashMap<>(5, 0.9f);
        map.put(key1, value1);
        map.put(key2, value2);
        map.put(key3, value3);
        map.put(key4, value4);
        return map;
    }

}
