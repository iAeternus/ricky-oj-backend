package org.ricky.core.user.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.ricky.common.mongo.MongoBaseRepository;
import org.ricky.core.user.domain.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import static org.ricky.common.constants.CommonConstants.USER_CACHE;
import static org.ricky.common.utils.ValidationUtils.requireNonBlank;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/28
 * @className MongoCachedUserRepository
 * @desc
 */
@Slf4j
@Repository
public class MongoCachedUserRepository extends MongoBaseRepository<User> {

    @Cacheable(value = USER_CACHE, key = "#userId")
    public User cachedById(String userId) {
        requireNonBlank(userId, "User ID must not be blank.");

        return super.byId(userId);
    }

    @Caching(evict = {@CacheEvict(value = USER_CACHE, key = "#userId")})
    public void evictUserCache(String userId) {
        requireNonBlank(userId, "User ID must not be blank.");

        log.info("Evicted cache for user[{}].", userId);
    }

}
