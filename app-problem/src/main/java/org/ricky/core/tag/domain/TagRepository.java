package org.ricky.core.tag.domain;

import org.ricky.common.context.UserContext;

import java.util.List;

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

    Tag byIdAndCheckUserShip(String tagId, UserContext userContext);

    void delete(Tag tag);

    boolean exists(String tagId);

    List<CachedTag> cachedAll();

}
