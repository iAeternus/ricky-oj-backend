package org.ricky.core.problem.domain.setting;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.ricky.common.validation.collection.NoNullElement;
import org.ricky.common.domain.LanguageEnum;
import org.ricky.core.problem.domain.ProblemSettingContext;
import org.ricky.core.problem.domain.answer.Answer;
import org.ricky.core.problem.domain.casegroup.CaseGroup;
import org.ricky.core.problem.domain.setting.limit.Limit;

import java.util.List;

import static org.ricky.common.domain.LanguageEnum.*;
import static org.ricky.core.problem.domain.setting.ApplyPublicProgressEnum.NOT_APPLIED_YET;
import static org.ricky.core.problem.domain.setting.GroupSetting.defaultGroupSetting;
import static org.ricky.core.problem.domain.setting.ProblemDifficultyEnum.EASY;
import static org.ricky.core.problem.domain.setting.ProblemStatusEnum.PRIVATE;
import static org.ricky.core.problem.domain.setting.ProblemTypeEnum.ACM;
import static org.ricky.core.problem.domain.setting.SPJSetting.defaultSPJSetting;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/26
 * @className ProblemSetting
 * @desc 题目设置
 */
@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProblemSetting {

    public static final List<LanguageEnum> ALL_LANGUAGES = List.of(C_11, CPP_17, CPP_20, CPP_23, GOLANG, JAVA_8, JAVA_21, PYTHON_2, PYTHON_3, RUST);

    /**
     * 题目类型
     */
    @NotNull
    private ProblemTypeEnum type;

    /**
     * 评测模式
     */
    @NotNull
    private JudgeModeEnum judgeMode;

    /**
     * 评测样例模式
     */
    @NotNull
    private JudgeCaseModeEnum judgeCaseMode;

    /**
     * 题目难度
     */
    @NotNull
    private ProblemDifficultyEnum difficulty;

    /**
     * 题目状态
     */
    @NotNull
    private ProblemStatusEnum status;

    /**
     * 限制
     */
    @Valid
    private Limit limit;

    /**
     * 允许的作答语言集合
     */
    @NotNull
    @NoNullElement
    private List<LanguageEnum> languages;

    /**
     * 是否开启该题目的测试样例结果查看
     */
    boolean openCaseResult;

    /**
     * 团队设置
     */
    private GroupSetting groupSetting;

    /**
     * 申请公开的进度
     */
    @NotNull
    private ApplyPublicProgressEnum applyPublicProgress;

    /**
     * 远程评测设置
     */
    @Valid
    private VJSetting vjSetting;

    /**
     * 特判程序或交互程序的设置
     */
    @Valid
    private SPJSetting spjSetting;

    /**
     * 测试用例组集合
     */
    private List<CaseGroup> caseGroups;

    /**
     * 答案集合
     */
    private List<Answer> answers;

    public ProblemSettingContext context() {
        return new ProblemSettingContext(this);
    }

    public static ProblemSetting defaultProblemSetting() {
        return ProblemSetting.builder()
                .type(ACM)
                .judgeMode(JudgeModeEnum.DEFAULT)
                .judgeCaseMode(JudgeCaseModeEnum.DEFAULT)
                .difficulty(EASY)
                .status(PRIVATE)
                .limit(Limit.defaultLimit())
                .languages(ALL_LANGUAGES)
                .openCaseResult(false)
                .groupSetting(defaultGroupSetting())
                .applyPublicProgress(NOT_APPLIED_YET)
                .vjSetting(VJSetting.defaultVJSetting())
                .spjSetting(defaultSPJSetting())
                .caseGroups(List.of())
                .answers(List.of())
                .build();
    }

    public boolean isACMFormat() {
        return type == ACM;
    }

}
