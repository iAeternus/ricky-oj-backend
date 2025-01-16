package org.ricky.core.judge.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.ricky.common.mongo.MongoBaseRepository;
import org.ricky.core.judge.domain.Judge;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import static org.ricky.common.constants.CommonConstants.JUDGE_CACHE;
import static org.ricky.common.utils.ValidationUtils.requireNonBlank;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/7
 * @className MongoCachedJudgeRepository
 * @desc
 */
@Slf4j
@Repository
public class MongoCachedJudgeRepository extends MongoBaseRepository<Judge> {

    @Cacheable(value = JUDGE_CACHE, key = "#judgeId")
    public Judge cachedById(String judgeId) {
        requireNonBlank(judgeId, "Judge ID must not be blank.");

        return super.byId(judgeId);
    }

    @Caching(evict = {@CacheEvict(value = JUDGE_CACHE, key = "#judgeId")})
    public void evictJudgeCache(String judgeId) {
        requireNonBlank(judgeId, "Judge ID must not be blank.");

        log.info("Evicted cache for judge[{}].", judgeId);
    }

}
