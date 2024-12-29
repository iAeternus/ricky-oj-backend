package org.ricky.core.user.infrastructure;

import lombok.RequiredArgsConstructor;
import org.ricky.common.mongo.MongoBaseRepository;
import org.ricky.core.user.domain.User;
import org.ricky.core.user.domain.UserRepository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.ricky.common.utils.ValidationUtils.requireNonBlank;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/28
 * @className MongoUserRepository
 * @desc
 */
@Repository
@RequiredArgsConstructor
public class MongoUserRepository extends MongoBaseRepository<User> implements UserRepository {

    private final MongoCachedUserRepository cachedUserRepository;

    @Override
    public User cachedById(String userId) {
        return cachedUserRepository.cachedById(userId);
    }

    @Override
    public boolean existsByMobileOrEmail(String mobileOrEmail) {
        requireNonBlank(mobileOrEmail, "Mobile or email must not be blank.");

        Criteria criteria = new Criteria();
        criteria.orOperator(where("mobile").is(mobileOrEmail), where("email").is(mobileOrEmail));
        return mongoTemplate.exists(new Query(criteria), User.class);
    }

    @Override
    public Optional<User> byMobileOrEmailOptional(String mobileOrEmail) {
        requireNonBlank(mobileOrEmail, "Mobile or email must not be blank.");

        Criteria criteria = new Criteria();
        criteria.orOperator(where("mobile").is(mobileOrEmail), where("email").is(mobileOrEmail));
        return ofNullable(mongoTemplate.findOne(query(criteria), User.class));
    }
}
