package org.ricky.core.user.domain;

import lombok.RequiredArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.exception.MyException;
import org.springframework.stereotype.Service;

import static org.ricky.common.exception.ErrorCodeEnum.STUDENT_WITH_MOBILE_OR_EMAIL_ALREADY_EXISTS;
import static org.ricky.common.utils.CollectionUtils.mapOf;
import static org.ricky.core.common.utils.MobileOrEmailUtils.isMobileNumber;
import static org.ricky.core.common.utils.MobileOrEmailUtils.maskMobileOrEmail;

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
            throw new MyException(STUDENT_WITH_MOBILE_OR_EMAIL_ALREADY_EXISTS, "注册失败，手机号或邮箱已被占用。",
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

}
