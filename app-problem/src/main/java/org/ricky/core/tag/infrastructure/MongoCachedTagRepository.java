package org.ricky.core.tag.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.ricky.common.mongo.MongoBaseRepository;
import org.ricky.core.tag.domain.CachedTag;
import org.ricky.core.tag.domain.Tag;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

import static org.ricky.common.constants.CommonConstants.TAGS_CACHE;
import static org.ricky.common.constants.CommonConstants.TAG_COLLECTION;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/5
 * @className MongoCachedTagRepository
 * @desc
 */
@Slf4j
@Repository
public class MongoCachedTagRepository extends MongoBaseRepository<Tag> {

    @Cacheable(value = TAGS_CACHE)
    public ArrayList<CachedTag> cachedAllTags() {
        Query query = new Query();
        query.fields().include("name", "color", "oj", "groupId");
        return new ArrayList<>(mongoTemplate.find(query, CachedTag.class, TAG_COLLECTION));
    }

    @Caching(evict = {@CacheEvict(value = TAGS_CACHE)})
    public void evictTagsCache() {
        log.info("Evicted all tags cache.");
    }

}
