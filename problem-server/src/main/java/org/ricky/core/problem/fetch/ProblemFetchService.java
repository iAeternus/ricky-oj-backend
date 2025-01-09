package org.ricky.core.problem.fetch;

import lombok.RequiredArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.ratelimit.RateLimiter;
import org.ricky.core.problem.domain.Problem;
import org.ricky.core.problem.domain.ProblemRepository;
import org.ricky.core.problem.fetch.response.FetchProblemByIdResponse;
import org.ricky.core.problem.fetch.response.FetchSettingByIdResponse;
import org.springframework.stereotype.Service;

import static org.ricky.common.ratelimit.TPSConstants.NORMAL_TPS;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/26
 * @className ProblemFetchService
 * @desc
 */
@Service
@RequiredArgsConstructor
public class ProblemFetchService {

    private final RateLimiter rateLimiter;
    private final ProblemRepository problemRepository;

    public FetchProblemByIdResponse fetchById(String problemId, UserContext userContext) {
        rateLimiter.applyFor(userContext.getUserId(), "Problem:FetchById", NORMAL_TPS);

        Problem problem = problemRepository.cachedById(problemId);
        return FetchProblemByIdResponse.builder()
                .customId(problem.getCustomId())
                .title(problem.getTitle())
                .author(problem.getAuthor())
                .description(problem.getDescription())
                .inputFormat(problem.getInputFormat())
                .outputCases(problem.getOutputCases())
                .inputCases(problem.getInputCases())
                .outputCases(problem.getOutputCases())
                .hint(problem.getHint())
                .build();
    }

    public FetchSettingByIdResponse fetchSettingById(String problemId, UserContext userContext) {
        rateLimiter.applyFor(userContext.getUserId(), "Problem:FetchSettingById", NORMAL_TPS);

        Problem problem = problemRepository.cachedById(problemId);
        return FetchSettingByIdResponse.builder()
                .type(problem.getSetting().getType())
                .judgeMode(problem.getSetting().getJudgeMode())
                .judgeCaseMode(problem.getSetting().getJudgeCaseMode())
                .difficulty(problem.getSetting().getDifficulty())
                .status(problem.getSetting().getStatus())
                .timeLimit(problem.getSetting().getLimit().getTimeLimit())
                .memoryLimit(problem.getSetting().getLimit().getMemoryLimit())
                .stackLimit(problem.getSetting().getLimit().getStackLimit())
                .languages(problem.getSetting().getLanguages())
                .openCaseResult(problem.getSetting().getOpenCaseResult())
                .applyPublicProgress(problem.getSetting().getApplyPublicProgress())
                .isRemote(problem.getSetting().getVjSetting().isRemote())
                .source(problem.getSetting().getVjSetting().getSource())
                .spjCode(problem.getSetting().getSpjSetting().getSpjCode())
                .spjLanguage(problem.getSetting().getSpjSetting().getSpjLanguage())
                .userExtraFile(problem.getSetting().getSpjSetting().getUserExtraFile())
                .judgeExtraFile(problem.getSetting().getSpjSetting().getJudgeExtraFile())
                .caseGroups(problem.getSetting().getCaseGroups())
                .answers(problem.getSetting().getAnswers())
                .build();
    }
}
