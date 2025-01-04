package org.ricky.core.problem.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.ricky.common.mongo.MongoBaseRepository;
import org.ricky.core.problem.domain.CachedProblem;
import org.ricky.core.problem.domain.Problem;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

import static org.ricky.common.constants.CommonConstants.PROBLEMS_CACHE;
import static org.ricky.common.constants.CommonConstants.PROBLEM_COLLECTION;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/2
 * @className MongoCachedProblemRepository
 * @desc
 */
@Slf4j
@Repository
public class MongoCachedProblemRepository extends MongoBaseRepository<Problem> {

    /**
     * 必须返回ArrayList而非List，否则缓存中由于没有ArrayList类型信息而失败
     * Problem是一个读密集型对象，故可以缓存所有
     */
    @Cacheable(value = PROBLEMS_CACHE)
    public ArrayList<CachedProblem> cachedAllProblems() {
        Query query = new Query();
        query.fields().include("customId", "title", "author", "description", "inputFormat", "outputFormat", "inputCases", "outputCases", "hint");
        return new ArrayList<>(mongoTemplate.find(query, CachedProblem.class, PROBLEM_COLLECTION));
    }

    @Caching(evict = {@CacheEvict(value = PROBLEMS_CACHE)})
    public void evictProblemsCache() {
        log.info("Evicted all problems cache.");
    }

}
