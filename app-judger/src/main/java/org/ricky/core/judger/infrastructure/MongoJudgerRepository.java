package org.ricky.core.judger.infrastructure;

import org.ricky.common.mongo.MongoBaseRepository;
import org.ricky.core.judger.domain.Judger;
import org.ricky.core.judger.domain.JudgerRepository;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/6
 * @className MongoJudgerRepository
 * @desc
 */
@Repository
public class MongoJudgerRepository extends MongoBaseRepository<Judger> implements JudgerRepository {

    @Override
    public Judger byUrl(String ip, Integer port) {
        Query query = query(where("ip").is(ip).and("port").is(port));
        return mongoTemplate.findOne(query, Judger.class);
    }

    @Override
    public void save(Judger judger) {
        super.save(judger);
    }
}
