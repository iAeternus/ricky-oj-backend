package org.ricky.core.verification.alter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ricky.common.context.UserContext;
import org.ricky.common.ratelimit.RateLimiter;
import org.ricky.core.user.domain.UserRepository;
import org.ricky.core.verification.alter.dto.command.CreateLoginVerificationCodeCommand;
import org.ricky.core.verification.alter.dto.command.CreateRegisterVerificationCodeCommand;
import org.ricky.core.verification.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.ricky.common.context.UserContext.NOUSER;
import static org.ricky.common.ratelimit.TPSConstants.*;
import static org.ricky.core.common.utils.MobileOrEmailUtils.maskMobileOrEmail;
import static org.ricky.core.verification.domain.VerificationCodeTypeEnum.REGISTER;


/**
 * @author Ricky
 * @version 1.0
 * @date 2024/9/24
 * @className VerificationCodeAlterationService
 * @desc
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationCodeAlterationService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final VerificationCodeFactory verificationCodeFactory;
    private final VerificationCodeSender verificationCodeSender;
    private final UserRepository userRepository;
    private final RateLimiter rateLimiter;

    @Transactional
    public String createVerificationCodeForRegister(CreateRegisterVerificationCodeCommand command) {
        String mobileOrEmail = command.getMobileOrEmail();
        rateLimiter.applyFor("VerificationCode:Register:All", NORMAL_TPS);
        rateLimiter.applyFor("VerificationCode:Register:" + mobileOrEmail, MIN_TPS);

        if (userRepository.existsByMobileOrEmail(mobileOrEmail)) {
            log.warn("[{}] already exists for register.", maskMobileOrEmail(mobileOrEmail));
            return VerificationCode.newVerificationCodeId();
        }

        String verificationCodeId = createVerificationCode(mobileOrEmail, REGISTER, null, NOUSER);
        log.info("Created verification code[{}] for register for [{}].", verificationCodeId, maskMobileOrEmail(command.getMobileOrEmail()));
        return verificationCodeId;
    }

    @Transactional
    public String createVerificationCodeForLogin(CreateLoginVerificationCodeCommand command) {
        String mobileOrEmail = command.getMobileOrEmail();
        rateLimiter.applyFor("VerificationCode:Login:All", EXTREMELY_HIGH_TPS);
        rateLimiter.applyFor("VerificationCode:Login:" + mobileOrEmail, MIN_TPS);

        String verificationCodeId = userRepository.byMobileOrEmailOptional(mobileOrEmail)
                .map(user -> createVerificationCode(mobileOrEmail, VerificationCodeTypeEnum.LOGIN, user.getId(), NOUSER))
                .orElseGet(() -> {
                    log.warn("No user exists for [{}] for login.", maskMobileOrEmail(mobileOrEmail));
                    return VerificationCode.newVerificationCodeId();
                });

        log.info("Created verification code[{}] for login for [{}].", verificationCodeId, maskMobileOrEmail(command.getMobileOrEmail()));
        return verificationCodeId;
    }

    private String createVerificationCode(String mobileOrEmail, VerificationCodeTypeEnum type, String userId, UserContext userContext) {
        Optional<VerificationCode> verificationCodeOptional = verificationCodeFactory.create(mobileOrEmail, type, userId, userContext);
        if (verificationCodeOptional.isPresent()) {
            VerificationCode code = verificationCodeOptional.get();
            verificationCodeRepository.save(code);
            verificationCodeSender.send(code);
            return code.getId();
        } else {
            return VerificationCode.newVerificationCodeId();
        }
    }
}
