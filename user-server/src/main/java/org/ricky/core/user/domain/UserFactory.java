package org.ricky.core.user.domain;

import lombok.RequiredArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.password.MyPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/30
 * @className UserFactory
 * @desc
 */
@Component
@RequiredArgsConstructor
public class UserFactory {

    private final MyPasswordEncoder passwordEncoder;

    public User create(String nickname, String mobile, String email, String password, UserContext userContext) {
        return new User(nickname, passwordEncoder.encode(password), email, mobile, userContext);
    }

}
