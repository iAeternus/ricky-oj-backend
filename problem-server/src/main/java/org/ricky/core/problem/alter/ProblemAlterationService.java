package org.ricky.core.problem.alter;

import lombok.RequiredArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.ratelimit.RateLimiter;
import org.ricky.core.problem.alter.dto.command.CreateProblemCommand;
import org.ricky.core.problem.alter.dto.response.CreateProblemResponse;
import org.ricky.core.problem.domain.Problem;
import org.ricky.core.problem.domain.ProblemDomainService;
import org.ricky.core.problem.domain.ProblemFactory;
import org.ricky.core.problem.domain.ProblemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.ricky.common.ratelimit.TPSConstants.MIN_TPS;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/26
 * @className ProblemAlterationService
 * @desc
 */
@Service
@RequiredArgsConstructor
public class ProblemAlterationService {

    private final RateLimiter rateLimiter;
    private final ProblemDomainService problemDomainService;
    private final ProblemFactory problemFactory;
    private final ProblemRepository problemRepository;

    @Transactional
    public CreateProblemResponse createProblem(CreateProblemCommand command, UserContext userContext) {
        rateLimiter.applyFor("Problem:CreateProblem", MIN_TPS);

        problemDomainService.checkCustomIdDuplication(command.getCustomId());
        Problem problem = problemFactory.create(command, userContext);
        problemRepository.save(problem);

        return CreateProblemResponse.builder()
                .problemId(problem.getId())
                .build();
    }
}
