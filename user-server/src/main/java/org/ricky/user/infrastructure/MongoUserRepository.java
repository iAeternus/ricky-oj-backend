package org.ricky.user.infrastructure;

import lombok.RequiredArgsConstructor;
import org.ricky.common.mongo.MongoBaseRepository;
import org.ricky.user.domain.User;
import org.ricky.user.domain.UserRepository;
import org.springframework.stereotype.Repository;

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
}
