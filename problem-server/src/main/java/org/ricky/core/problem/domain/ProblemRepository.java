package org.ricky.core.problem.domain;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/2
 * @className ProblemRepository
 * @desc
 */
public interface ProblemRepository {
    boolean cachedExistsByCustomId(String customId);

    void save(Problem problem);
}
