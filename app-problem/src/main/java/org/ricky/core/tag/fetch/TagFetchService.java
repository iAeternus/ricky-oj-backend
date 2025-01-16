package org.ricky.core.tag.fetch;

import lombok.RequiredArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.ratelimit.RateLimiter;
import org.ricky.core.tag.domain.CachedTag;
import org.ricky.core.tag.domain.TagRepository;
import org.ricky.core.tag.fetch.response.FetchAllResponse;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.ricky.common.ratelimit.TPSConstants.EXTREMELY_LOW_TPS;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/5
 * @className TagFetchService
 * @desc
 */
@Service
@RequiredArgsConstructor
public class TagFetchService {

    private final RateLimiter rateLimiter;
    private final TagRepository tagRepository;

    public FetchAllResponse fetchAll(UserContext userContext) {
        rateLimiter.applyFor(userContext.getUserId(), "Tag:FetchAll", EXTREMELY_LOW_TPS);

        List<CachedTag> cachedTags = tagRepository.cachedAll();
        return FetchAllResponse.builder()
                .tags(cachedTags)
                .build();
    }
}
