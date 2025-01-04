package org.ricky.core.user.domain;

import lombok.RequiredArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.exception.MyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.ricky.common.exception.ErrorCodeEnum.USER_WITH_MOBILE_OR_EMAIL_ALREADY_EXISTS;
import static org.ricky.common.utils.CollectionUtils.mapOf;
import static org.ricky.core.common.utils.MobileOrEmailUtils.isMobileNumber;
import static org.ricky.core.common.utils.MobileOrEmailUtils.maskMobileOrEmail;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/30
 * @className UserDomainService
 * @desc
 */
@Service
@RequiredArgsConstructor
public class UserDomainService {

    private final UserRepository userRepository;
    private final UserFactory userFactory;

    public User register(String nickname, String mobileOrEmail, String password, UserContext userContext) {
        if (userRepository.existsByMobileOrEmail(mobileOrEmail)) {
            throw new MyException(USER_WITH_MOBILE_OR_EMAIL_ALREADY_EXISTS, "注册失败，手机号或邮箱已被占用。",
                    mapOf("mobileOrEmail", maskMobileOrEmail(mobileOrEmail)));
        }

        String mobile = null;
        String email = null;
        if (isMobileNumber(mobileOrEmail)) {
            mobile = mobileOrEmail;
        } else {
            email = mobileOrEmail;
        }

        return userFactory.create(nickname, mobile, email, password, userContext);
    }

    // 使用REQUIRES_NEW保证即便其他地方有异常，这里也能正常写库
    @Transactional(propagation = REQUIRES_NEW)
    public void recordUserFailedLogin(User user) {
        user.recordFailedLogin();
        userRepository.save(user);
    }

}
