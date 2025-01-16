package org.ricky.core.login.domain;

import lombok.RequiredArgsConstructor;
import org.ricky.common.exception.MyException;
import org.ricky.common.password.MyPasswordEncoder;
import org.ricky.common.security.jwt.JwtService;
import org.ricky.core.user.domain.User;
import org.ricky.core.user.domain.UserDomainService;
import org.ricky.core.user.domain.UserRepository;
import org.ricky.core.verification.domain.VerificationCodeChecker;
import org.springframework.stereotype.Service;

import static org.ricky.common.exception.MyException.authenticationException;
import static org.ricky.core.verification.domain.VerificationCodeTypeEnum.LOGIN;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/30
 * @className LoginDomainService
 * @desc
 */
@Service
@RequiredArgsConstructor
public class LoginDomainService {

    private final UserRepository userRepository;
    private final MyPasswordEncoder passwordEncoder;
    private final UserDomainService userDomainService;
    private final JwtService jwtService;
    private final VerificationCodeChecker verificationCodeChecker;

    public String loginWithMobileOrEmail(String mobileOrEmail, String password) {
        User user = userRepository.byMobileOrEmailOptional(mobileOrEmail)
                .orElseThrow(MyException::authenticationException);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            userDomainService.recordUserFailedLogin(user);
            throw authenticationException();
        }

        user.checkActive();
        return jwtService.generateJwt(user.getId());
    }

    public String loginWithVerificationCode(String mobileOrEmail, String verification) {
        verificationCodeChecker.check(mobileOrEmail, verification, LOGIN);
        User user = userRepository.byMobileOrEmailOptional(mobileOrEmail)
                .orElseThrow(MyException::authenticationException);

        user.checkActive();
        return jwtService.generateJwt(user.getId());
    }
}
