package org.ricky.core.tag.domain;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/5
 * @className TagRepository
 * @desc
 */
public interface TagRepository {
    boolean existsByName(String name);

    void save(Tag tag);

    Tag byId(String tagId);
}
