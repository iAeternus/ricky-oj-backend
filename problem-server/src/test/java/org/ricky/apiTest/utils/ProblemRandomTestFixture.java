package org.ricky.apiTest.utils;

import org.ricky.common.utils.RandomTestFixture;
import org.ricky.core.problem.alter.dto.command.CreateProblemCommand;
import org.ricky.core.problem.domain.answer.Answer;
import org.ricky.core.problem.domain.casegroup.CaseGroup;
import org.ricky.core.problem.domain.casegroup.cases.Case;
import org.ricky.core.problem.domain.setting.*;
import org.ricky.core.problem.domain.setting.limit.Limit;

import java.util.List;

import static org.ricky.common.constants.CommonConstants.*;
import static org.ricky.common.constants.CommonConstants.MAX_GENERIC_TEXT_LENGTH;
import static org.ricky.core.problem.domain.LanguageEnum.CPP_23;
import static org.ricky.core.problem.domain.casegroup.CaseGroup.newCaseGroupId;
import static org.ricky.core.problem.domain.casegroup.cases.Case.newCaseId;
import static org.ricky.core.problem.domain.setting.ProblemSetting.ALL_LANGUAGES;
import static org.ricky.management.SystemManager.newId;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/4
 * @className ProblemRandomTestFixture
 * @desc
 */
public class ProblemRandomTestFixture extends RandomTestFixture {

    public static CreateProblemCommand rCreateProblemCommand(String customId) {
        return CreateProblemCommand.builder()
                .customId(customId)
                .title(rSentence(MAX_GENERIC_NAME_LENGTH))
                .author(rNickname())
                .description(rSentence(MAX_GENERIC_TEXT_LENGTH))
                .inputFormat(rSentence(MAX_GENERIC_TEXT_LENGTH))
                .outputFormat(rSentence(MAX_GENERIC_TEXT_LENGTH))
                .inputCases(List.of(rInputCase(2)))
                .outputCases(List.of(rOutputCase(2)))
                .hint(rSentence(MAX_GENERIC_TEXT_LENGTH))
                .build();
    }

    public static CreateProblemCommand rCreateProblemCommand() {
        return rCreateProblemCommand(rCustomId());
    }

    public static Limit rLimit() {
        return Limit.builder()
                .timeLimit(rDouble(0, Double.MAX_VALUE))
                .memoryLimit(rDouble(0, Double.MAX_VALUE))
                .stackLimit(rInt(0, 8))
                .build();
    }

    public static GroupSetting rGroupSetting() {
        boolean isGroup = rBool();
        return GroupSetting.builder()
                .isGroup(isGroup)
                .teamId(isGroup ? newId(GROUP_ID_PREFIX) : null)
                .build();
    }

    public static VJSetting rVJSetting() {
        return VJSetting.builder().build();
    }

    public static SPJSetting rSPJSetting() {
        return SPJSetting.builder().build();
    }

    public static Case rCase() {
        return Case.builder()
                .id(newCaseId())
                .input(rInputCase(5))
                .output(rOutputCase(5))
                .seq(rInt(1, Integer.MAX_VALUE - 1))
                .build();
    }

    public static CaseGroup rCaseGroup() {
        return CaseGroup.builder()
                .id(newCaseGroupId())
                .name(rSentence(5))
                .cases(rList(rInt(1, 10), ProblemRandomTestFixture::rCase))
                .build();
    }

    public static Answer rAnswer() {
        String code = """
                #include <bits/stdc++.h>
                                        
                using i64 = long long;
                                        
                void solve() {
                    std::cout << "This is a C++ test code." << std::endl;
                }
                                        
                int main() {
                    std::ios::sync_with_stdio(false);
                    std::cin.tie(nullptr);
                    std::cout.tie(nullptr);
                                        
                    int t;
                    std::cin >> t;
                                        
                    while(t--) {
                        solve();
                    }
                                        
                    return 0;
                }
                """;
        return Answer.builder()
                .language(CPP_23)
                .code(code)
                .enable(rBool())
                .build();
    }

    public static ProblemSetting rProblemSetting() {
        ProblemTypeEnum type = rEnumOf(ProblemTypeEnum.class);
        return ProblemSetting.builder()
                .type(type)
                .judgeMode(rEnumOf(JudgeModeEnum.class))
                .judgeCaseMode(rEnumOf(JudgeCaseModeEnum.class))
                .difficulty(rEnumOf(ProblemDifficultyEnum.class))
                .status(rEnumOf(ProblemStatusEnum.class))
                .limit(rLimit())
                .languages(ALL_LANGUAGES)
                .openCaseResult(rBool())
                .groupSetting(rGroupSetting())
                .applyPublicProgress(rEnumOf(ApplyPublicProgressEnum.class))
                .vjSetting(rVJSetting())
                .spjSetting(rSPJSetting())
                .caseGroups(rList(rInt(1, 3), ProblemRandomTestFixture::rCaseGroup))
                .answers(rList(rInt(1, 2), ProblemRandomTestFixture::rAnswer))
                .build();
    }

    public static void main(String[] args) {
        System.out.println(rAnswer().getCode());
    }

}
