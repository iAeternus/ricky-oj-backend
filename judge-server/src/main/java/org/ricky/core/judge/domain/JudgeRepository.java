package org.ricky.core.judge.domain;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/7
 * @className JudgeRepository
 * @desc
 */
public interface JudgeRepository {
    void save(Judge judge);

    Judge cachedById(String judgeId);

    Judge byId(String judgeId);
}
