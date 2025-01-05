package org.ricky.core.tag.alter;

import lombok.RequiredArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.ratelimit.RateLimiter;
import org.ricky.common.ratelimit.TPSConstants;
import org.ricky.core.tag.alter.dto.command.CreateProblemTagCommand;
import org.ricky.core.tag.alter.dto.response.CreateProblemTagResponse;
import org.ricky.core.tag.domain.Tag;
import org.ricky.core.tag.domain.TagDomainService;
import org.ricky.core.tag.domain.TagFactory;
import org.ricky.core.tag.domain.TagRepository;
import org.springframework.stereotype.Service;

import static org.ricky.common.ratelimit.TPSConstants.MIN_TPS;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/5
 * @className TagAlterationService
 * @desc
 */
@Service
@RequiredArgsConstructor
public class TagAlterationService {

    private final RateLimiter rateLimiter;
    private final TagDomainService tagDomainService;
    private final TagRepository tagRepository;
    private final TagFactory tagFactory;

    public CreateProblemTagResponse createProblemTag(CreateProblemTagCommand command, UserContext userContext) {
        rateLimiter.applyFor(userContext.getUserId(), "Tag:CreateProblemTag", MIN_TPS);

        tagDomainService.checkNameDuplication(command.getName());
        Tag tag = tagFactory.create(command, userContext);
        tagRepository.save(tag);

        return CreateProblemTagResponse.builder()
                .tagId(tag.getId())
                .build();
    }
}
