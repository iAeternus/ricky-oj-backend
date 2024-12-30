package org.ricky.core.user.domain;

import java.util.Optional;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/28
 * @className UserRepository
 * @desc
 */
public interface UserRepository {
    User cachedById(String userId);

    boolean existsByMobileOrEmail(String mobileOrEmail);

    Optional<User> byMobileOrEmailOptional(String mobileOrEmail);

    void save(User user);
}
