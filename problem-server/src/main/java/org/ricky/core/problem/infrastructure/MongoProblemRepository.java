package org.ricky.core.problem.infrastructure;

import lombok.RequiredArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.mongo.MongoBaseRepository;
import org.ricky.common.utils.ValidationUtils;
import org.ricky.core.problem.domain.Problem;
import org.ricky.core.problem.domain.ProblemRepository;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.ricky.common.utils.ValidationUtils.isEmpty;
import static org.ricky.common.utils.ValidationUtils.requireNonBlank;
import static org.springframework.data.mongodb.core.BulkOperations.BulkMode.UNORDERED;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

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

    @Override
    public Problem byIdAndCheckUserShip(String id, UserContext userContext) {
        return super.byIdAndCheckUserShip(id, userContext);
    }

    @Override
    public Problem byId(String id) {
        return super.byId(id);
    }

    @Override
    public List<Problem> findByTagId(String tagId) {
        Query query = query(where("tags").is(tagId));
        return mongoTemplate.find(query, Problem.class);
    }

    @Override
    public void insert(List<Problem> problems) {
        super.insert(problems);
        cachedProblemRepository.evictProblemsCache();
    }

    @Override
    public void updateProblemTags(List<Problem> problems) {
        if (isEmpty(problems)) {
            return;
        }

        BulkOperations bulkOps = mongoTemplate.bulkOps(UNORDERED, Problem.class);
        problems.forEach(problem -> {
            Query query = new Query(Criteria.where("_id").is(problem.getId()));
            Update update = new Update();
            update.set("tags", problem.getTags());
            bulkOps.upsert(query, update);
        });
        bulkOps.execute();
    }
}
