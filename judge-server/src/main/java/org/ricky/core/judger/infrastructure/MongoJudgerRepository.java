package org.ricky.core.judger.infrastructure;

import org.ricky.common.mongo.MongoBaseRepository;
import org.ricky.core.judger.domain.Judger;
import org.ricky.core.judger.domain.JudgerRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/6
 * @className MongoJudgerRepository
 * @desc
 */
@Repository
public class MongoJudgerRepository extends MongoBaseRepository<Judger> implements JudgerRepository {
}
