package org.ricky.core.problem.alter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ricky.common.context.UserContext;
import org.ricky.common.ratelimit.RateLimiter;
import org.ricky.common.utils.ValidationUtils;
import org.ricky.core.problem.alter.dto.command.CreateProblemCommand;
import org.ricky.core.problem.alter.dto.command.UpdateProblemSettingCommand;
import org.ricky.core.problem.alter.dto.command.UpdateProblemTagsCommand;
import org.ricky.core.problem.alter.dto.response.CreateProblemResponse;
import org.ricky.core.problem.alter.dto.response.UpdateProblemTagsResponse;
import org.ricky.core.problem.domain.Problem;
import org.ricky.core.problem.domain.ProblemDomainService;
import org.ricky.core.problem.domain.ProblemFactory;
import org.ricky.core.problem.domain.ProblemRepository;
import org.ricky.core.problem.domain.setting.ProblemSetting;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.ricky.common.ratelimit.TPSConstants.EXTREMELY_LOW_TPS;
import static org.ricky.common.ratelimit.TPSConstants.MIN_TPS;
import static org.ricky.common.utils.CollectionUtils.listEquals;
import static org.ricky.core.problem.alter.dto.response.UpdateProblemTagsResponse.noChange;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/26
 * @className ProblemAlterationService
 * @desc
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProblemAlterationService {

    private final RateLimiter rateLimiter;
    private final ProblemDomainService problemDomainService;
    private final ProblemFactory problemFactory;
    private final ProblemRepository problemRepository;

    @Transactional
    public CreateProblemResponse createProblem(CreateProblemCommand command, UserContext userContext) {
        rateLimiter.applyFor(userContext.getUserId(), "Problem:CreateProblem", MIN_TPS);

        problemDomainService.checkCustomIdDuplication(command.getCustomId());
        Problem problem = problemFactory.create(command, userContext);
        problemRepository.save(problem);

        return CreateProblemResponse.builder()
                .problemId(problem.getId())
                .build();
    }

    @Transactional
    public String updateProblemSetting(String problemId, UpdateProblemSettingCommand command, UserContext userContext) {
        rateLimiter.applyFor(userContext.getUserId(), "Problem:UpdateProblemSetting", EXTREMELY_LOW_TPS);

        Problem problem = problemRepository.byIdAndCheckUserShip(problemId, userContext);

        ProblemSetting newSetting = command.getSetting();
        if (ValidationUtils.equals(problem.getSetting(), newSetting)) {
            return problem.getVersion();
        }

        problem.updateSetting(newSetting, command.getVersion(), userContext);

        problemRepository.save(problem);
        log.info("Updated setting for problem[{}] to version[{}].", problemId, problem.getVersion());

        return problem.getVersion();
    }

    @Transactional
    public UpdateProblemTagsResponse updateProblemTags(String problemId, UpdateProblemTagsCommand command, UserContext userContext) {
        rateLimiter.applyFor(userContext.getUserId(), "Problem:UpdateProblemTags", EXTREMELY_LOW_TPS);

        Problem problem = problemRepository.byIdAndCheckUserShip(problemId, userContext);
        if (listEquals(problem.getTags(), command.getTags())) {
            return noChange();
        }

        var updateTags = problem.updateTags(command.getTags(), userContext);
        problemRepository.save(problem);

        return UpdateProblemTagsResponse.builder()
                .added(updateTags.getFirst())
                .deleted(updateTags.getSecond())
                .build();
    }

}
