package org.ricky.user.domain;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/28
 * @className UserRepository
 * @desc
 */
public interface UserRepository {
    User cachedById(String userId);
}
