package org.ricky.core.judge.infrastructure;

import lombok.RequiredArgsConstructor;
import org.ricky.common.mongo.MongoBaseRepository;
import org.ricky.core.judge.domain.Judge;
import org.ricky.core.judge.domain.JudgeRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/7
 * @className MongoJudgeRepository
 * @desc
 */
@Repository
@RequiredArgsConstructor
public class MongoJudgeRepository extends MongoBaseRepository<Judge> implements JudgeRepository {

    private final MongoCachedJudgeRepository cachedJudgeRepository;

    @Override
    public void save(Judge judge) {
        super.save(judge);
        cachedJudgeRepository.evictJudgeCache(judge.getId());
    }

    @Override
    public Judge cachedById(String judgeId) {
        return cachedJudgeRepository.cachedById(judgeId);
    }
}
