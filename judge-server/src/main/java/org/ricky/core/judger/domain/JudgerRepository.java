package org.ricky.core.judger.domain;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/6
 * @className JudgerRepository
 * @desc
 */
public interface JudgerRepository {

    Judger byUrl(String ip, Integer port);

    void save(Judger judger);
}
