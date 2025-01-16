package org.ricky.core.user.fetch;

import lombok.RequiredArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.ratelimit.RateLimiter;
import org.ricky.core.user.domain.User;
import org.ricky.core.user.domain.UserRepository;
import org.ricky.core.user.fetch.response.UserInfoResponse;
import org.springframework.stereotype.Service;

import static org.ricky.common.ratelimit.TPSConstants.LOW_TPS;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/4
 * @className UserFetchService
 * @desc
 */
@Service
@RequiredArgsConstructor
public class UserFetchService {

    private final RateLimiter rateLimiter;
    private final UserRepository userRepository;

    public UserInfoResponse fetchMyUserInfo(UserContext userContext) {
        rateLimiter.applyFor(userContext.getUserId(), "User:FetchMyUserInfo", LOW_TPS);

        User user = userRepository.cachedById(userContext.getUserId());

        return UserInfoResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .userData(user.getUserData())
                .school(user.getSchool())
                .course(user.getCourse())
                .idNumber(user.getIdNumber())
                .gender(user.getGender())
                .realName(user.getRealName())
                .signature(user.getSignature())
                .title(user.getTitle())
                .build();
    }
}
