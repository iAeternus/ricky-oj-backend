package org.ricky.core.user.alter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ricky.common.context.UserContext;
import org.ricky.common.ratelimit.RateLimiter;
import org.ricky.core.user.alter.dto.command.RegisterCommand;
import org.ricky.core.user.alter.dto.response.RegisterResponse;
import org.ricky.core.user.domain.User;
import org.ricky.core.user.domain.UserDomainService;
import org.ricky.core.user.domain.UserRepository;
import org.ricky.core.verification.domain.VerificationCodeChecker;
import org.springframework.stereotype.Service;

import static org.ricky.common.context.RoleEnum.USER;
import static org.ricky.core.user.domain.User.newStudentId;
import static org.ricky.core.verification.domain.VerificationCodeTypeEnum.REGISTER;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/30
 * @className UserAlterService
 * @desc
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserAlterService {

    private final RateLimiter rateLimiter;
    private final UserDomainService userDomainService;
    private final VerificationCodeChecker verificationCodeChecker;
    private final UserRepository userRepository;

    public RegisterResponse register(RegisterCommand command) {
        rateLimiter.applyFor("User:Register", 20);

        String mobileOrEmail = command.getMobileOrEmail();
        verificationCodeChecker.check(mobileOrEmail, command.getVerification(), REGISTER);

        UserContext userContext = UserContext.createUser(newStudentId(), command.getNickname(), USER);
        User user = userDomainService.register(command.getNickname(), command.getMobileOrEmail(), command.getPassword(), userContext);

        userRepository.save(user);
        log.info("User[{}] registered.", user.getId());

        return RegisterResponse.builder()
                .studentId(user.getId())
                .build();
    }
}
