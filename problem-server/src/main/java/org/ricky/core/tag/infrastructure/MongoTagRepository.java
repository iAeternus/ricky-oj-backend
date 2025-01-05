package org.ricky.core.tag.infrastructure;

import lombok.RequiredArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.mongo.MongoBaseRepository;
import org.ricky.common.utils.ValidationUtils;
import org.ricky.core.tag.domain.CachedTag;
import org.ricky.core.tag.domain.Tag;
import org.ricky.core.tag.domain.TagRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.ricky.common.utils.ValidationUtils.requireNonBlank;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/5
 * @className MongoTagRepository
 * @desc
 */
@Repository
@RequiredArgsConstructor
public class MongoTagRepository extends MongoBaseRepository<Tag> implements TagRepository {

    private final MongoCachedTagRepository cachedTagRepository;

    @Override
    public boolean existsByName(String name) {
        requireNonBlank(name, "Tag name must not be blank.");

        return cachedTagRepository.cachedAllTags().stream()
                .anyMatch(tag -> ValidationUtils.equals(tag.getName(), name));
    }

    @Override
    public void save(Tag tag) {
        super.save(tag);
        cachedTagRepository.evictTagsCache();
    }

    @Override
    public Tag byId(String id) {
        return super.byId(id);
    }

    @Override
    public Tag byIdAndCheckUserShip(String id, UserContext userContext) {
        return super.byIdAndCheckUserShip(id, userContext);
    }

    @Override
    public void delete(Tag tag) {
        super.delete(tag);
        cachedTagRepository.evictTagsCache();
    }

    @Override
    public boolean exists(String arId) {
        return super.exists(arId);
    }

    @Override
    public List<CachedTag> cachedAll() {
        return cachedTagRepository.cachedAllTags();
    }
}
