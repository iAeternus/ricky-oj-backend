package org.ricky.core.tag.infrastructure;

import lombok.RequiredArgsConstructor;
import org.ricky.common.mongo.MongoBaseRepository;
import org.ricky.common.utils.ValidationUtils;
import org.ricky.core.tag.domain.Tag;
import org.ricky.core.tag.domain.TagRepository;
import org.springframework.stereotype.Repository;

import static org.ricky.common.utils.ValidationUtils.requireNonBlank;

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
}
