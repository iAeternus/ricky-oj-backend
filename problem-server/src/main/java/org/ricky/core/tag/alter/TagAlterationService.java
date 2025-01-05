package org.ricky.core.tag.alter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ricky.common.context.UserContext;
import org.ricky.common.ratelimit.RateLimiter;
import org.ricky.core.problem.domain.Problem;
import org.ricky.core.problem.domain.ProblemRepository;
import org.ricky.core.tag.alter.dto.command.CreateProblemTagCommand;
import org.ricky.core.tag.alter.dto.command.UpdateTagInfoCommand;
import org.ricky.core.tag.alter.dto.response.CreateProblemTagResponse;
import org.ricky.core.tag.domain.Tag;
import org.ricky.core.tag.domain.TagDomainService;
import org.ricky.core.tag.domain.TagFactory;
import org.ricky.core.tag.domain.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static org.ricky.common.ratelimit.TPSConstants.EXTREMELY_LOW_TPS;
import static org.ricky.common.ratelimit.TPSConstants.MIN_TPS;
import static org.ricky.common.utils.CollectionUtils.listToString;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/5
 * @className TagAlterationService
 * @desc
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TagAlterationService {

    private final RateLimiter rateLimiter;
    private final TagDomainService tagDomainService;
    private final TagRepository tagRepository;
    private final TagFactory tagFactory;
    private final ProblemRepository problemRepository;

    @Transactional
    public CreateProblemTagResponse createProblemTag(CreateProblemTagCommand command, UserContext userContext) {
        rateLimiter.applyFor(userContext.getUserId(), "Tag:CreateProblemTag", MIN_TPS);

        tagDomainService.checkNameDuplication(command.getName());
        Tag tag = tagFactory.create(command, userContext);
        tagRepository.save(tag);
        log.info("Create tag[{}]", tag.getId());

        return CreateProblemTagResponse.builder()
                .tagId(tag.getId())
                .build();
    }

    @Transactional
    public void removeProblemTag(String tagId, UserContext userContext) {
        rateLimiter.applyFor(userContext.getUserId(), "Tag:RemoveProblemTag", EXTREMELY_LOW_TPS);

        Tag tag = tagRepository.byIdAndCheckUserShip(tagId, userContext);

        // 删除problem中关联的该tag
        List<Problem> problems = problemRepository.findByTagId(tagId);
        problems.forEach(problem -> problem.deleteTag(tagId));
        problemRepository.updateProblemTags(problems);
        log.info("Deleted tag[{}] in problems[{}]", tagId,
                listToString(problems.stream().map(Problem::getId).collect(toImmutableList())));

        // 删除tag
        tag.onDelete(userContext);
        tagRepository.delete(tag);
        log.info("Deleted tag[{}].", tagId);
    }

    @Transactional
    public void updateProblemTag(String tagId, UpdateTagInfoCommand command, UserContext userContext) {
        rateLimiter.applyFor(userContext.getUserId(), "Tag:UpdateProblemTag", EXTREMELY_LOW_TPS);

        Tag tag = tagRepository.byId(tagId);
        tag.update(command.getName(), command.getColor(), command.getOj(), command.getGroupId(), userContext);
        tagRepository.save(tag);
        log.info("Update tag[{}]", tagId);
    }
}
