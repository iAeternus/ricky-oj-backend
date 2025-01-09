package org.ricky.core.judge.domain;

import jakarta.servlet.http.HttpServletRequest;
import org.ricky.common.context.UserContext;
import org.ricky.core.judge.alter.command.SubmitCommand;
import org.ricky.core.judge.domain.submit.Submit;
import org.ricky.core.judge.domain.submit.SubmitTypeEnum;
import org.ricky.core.judge.domain.submit.judgecase.JudgeCase;
import org.ricky.dto.fetch.response.FetchProblemByIdResponse;
import org.ricky.dto.fetch.response.FetchSettingByIdResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static org.ricky.core.common.utils.IPUtils.getUserIpAddr;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/7
 * @className JudgeFactory
 * @desc
 */
@Component
public class JudgeFactory {

    public Submit createSubmit(SubmitCommand command, FetchSettingByIdResponse setting, FetchProblemByIdResponse problem, UserContext userContext) {
        List<JudgeCase> judgeCases = setting.getCaseGroups().stream()
                .flatMap(caseGroup -> caseGroup.getCases().stream())
                .map(caze -> new JudgeCase(caze.getId(), caze.getInput(), caze.getOutput(), caze.getSeq()))
                .collect(toImmutableList());
        return Submit.builder()
                .problemId(command.getProblemId())
                .customId(problem.getCustomId())
                .nickname(userContext.getNickname())
                .share(command.getShare())
                .type(SubmitTypeEnum.of(command.getSubmitType()))
                .isRemote(command.getIsRemote())
                .program(command.getProgram())
                .judgeCases(judgeCases)
                .build();
    }

    public Judge createJudge(Submit submit, UserContext userContext) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        return new Judge(submit, getUserIpAddr(request), userContext);
    }

}
