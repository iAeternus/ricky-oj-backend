package org.ricky.core.problem.infrastructure;

import lombok.RequiredArgsConstructor;
import org.ricky.common.mongo.MongoBaseRepository;
import org.ricky.common.utils.ValidationUtils;
import org.ricky.core.problem.domain.ProblemRepository;
import org.ricky.core.problem.domain.Problem;
import org.springframework.stereotype.Repository;

import static org.ricky.common.utils.ValidationUtils.requireNonBlank;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/2
 * @className MongoProblemRepository
 * @desc
 */
@Repository
@RequiredArgsConstructor
public class MongoProblemRepository extends MongoBaseRepository<Problem> implements ProblemRepository {

    private final MongoCachedProblemRepository cachedProblemRepository;

    @Override
    public boolean cachedExistsByCustomId(String customId) {
        requireNonBlank(customId, "Problem Custom ID must not be blank.");

        return cachedProblemRepository.cachedAllProblems().stream()
                .anyMatch(problem -> ValidationUtils.equals(problem.getCustomId(), customId));
    }

    @Override
    public void save(Problem problem) {
        super.save(problem);
        cachedProblemRepository.evictProblemsCache();
    }

}
