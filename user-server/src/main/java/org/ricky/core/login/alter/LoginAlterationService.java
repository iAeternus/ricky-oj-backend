package org.ricky.core.login.alter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ricky.common.context.UserContext;
import org.ricky.common.exception.MyException;
import org.ricky.common.ratelimit.RateLimiter;
import org.ricky.common.security.jwt.JwtService;
import org.ricky.core.login.alter.command.MobileOrEmailLoginCommand;
import org.ricky.core.login.alter.command.VerificationCodeLoginCommand;
import org.ricky.core.login.domain.LoginDomainService;
import org.ricky.core.user.domain.User;
import org.ricky.core.user.domain.UserRepository;
import org.springframework.stereotype.Service;

import static org.ricky.common.exception.MyException.authenticationException;
import static org.ricky.common.ratelimit.TPSConstants.MIN_TPS;
import static org.ricky.core.common.utils.MobileOrEmailUtils.maskMobileOrEmail;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/30
 * @className LoginAlterationService
 * @desc
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginAlterationService {

    private final RateLimiter rateLimiter;
    private final LoginDomainService loginDomainService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public String loginWithMobileOrEmail(MobileOrEmailLoginCommand command) {
        String mobileOrEmail = command.getMobileOrEmail();
        rateLimiter.applyFor("Login:MobileOrEmail:" + mobileOrEmail, MIN_TPS);

        try {
            String token = loginDomainService.loginWithMobileOrEmail(mobileOrEmail, command.getPassword());
            log.info("User[{}] logged in using password.", maskMobileOrEmail(command.getMobileOrEmail()));
            return token;
        } catch (Throwable t) {
            // 401或409时直接抛出异常
            if (t instanceof MyException mryException &&
                    (mryException.getCode().getStatus() == 401 || mryException.getCode().getStatus() == 409)) {
                log.warn("Password login failed for [{}].", maskMobileOrEmail(mobileOrEmail));
                throw mryException;
            }

            // 其他情况直接一个笼统的异常
            log.warn("Password login failed for [{}].", maskMobileOrEmail(mobileOrEmail), t);
            throw authenticationException();
        }
    }

    public String loginWithVerificationCode(VerificationCodeLoginCommand command) {
        String mobileOrEmail = command.getMobileOrEmail();
        rateLimiter.applyFor("Login:MobileOrEmail:" + mobileOrEmail, MIN_TPS);

        try {
            String token = loginDomainService.loginWithVerificationCode(mobileOrEmail, command.getVerification());
            log.info("User[{}] logged in using verification code.", maskMobileOrEmail(command.getMobileOrEmail()));
            return token;
        } catch (Throwable t) {
            // 401或409时直接抛出异常
            if (t instanceof MyException mryException &&
                    (mryException.getCode().getStatus() == 401 || mryException.getCode().getStatus() == 409)) {
                log.warn("Verification code login failed for [{}].", maskMobileOrEmail(mobileOrEmail));
                throw mryException;
            }

            // 其他情况直接一个笼统的异常
            log.warn("Verification code login failed for [{}].", maskMobileOrEmail(mobileOrEmail), t);
            throw authenticationException();
        }
    }

    public String refreshToken(UserContext userContext) {
        rateLimiter.applyFor("Login:RefreshToken:All", 1000);

        User user = userRepository.cachedById(userContext.getUserId());
        log.info("User[{}] refreshed token.", user.getId());
        return jwtService.generateJwt(user.getId());
    }
}
