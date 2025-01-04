package org.ricky.core.problem.domain.setting;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.ricky.common.exception.MyException;
import org.ricky.common.validation.collection.NoNullElement;
import org.ricky.core.problem.domain.setting.limit.Limit;
import org.ricky.core.problem.domain.LanguageEnum;

import java.util.List;

import static org.ricky.common.exception.ErrorCodeEnum.ACM_FORMAT_DOES_NOT_NEED_SCORE;
import static org.ricky.core.problem.domain.LanguageEnum.*;
import static org.ricky.core.problem.domain.setting.ApplyPublicProgressEnum.NOT_APPLIED_YET;
import static org.ricky.core.problem.domain.setting.GroupSetting.defaultGroupSetting;
import static org.ricky.core.problem.domain.setting.ProblemDifficultyEnum.EASY;
import static org.ricky.core.problem.domain.setting.ProblemStatusEnum.PRIVATE;
import static org.ricky.core.problem.domain.setting.ProblemTypeEnum.ACM;
import static org.ricky.core.problem.domain.setting.ProblemTypeEnum.OI;
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

    public static final List<LanguageEnum> ALL = List.of(C_11, CPP_17, CPP_20, CPP_23, GOLANG, JAVA_8, JAVA_21, PYTHON_2, PYTHON_3, RUST);

    @NotNull
    @Schema(name = "题目类型")
    ProblemTypeEnum type;

    @NotNull
    @Schema(name = "评测模式")
    JudgeModeEnum judgeMode;

    @NotNull
    @Schema(name = "评测样例模式")
    JudgeCaseModeEnum judgeCaseMode;

    @NotNull
    @Schema(name = "题目难度")
    ProblemDifficultyEnum difficulty;

    @NotNull
    @Schema(name = "题目状态")
    ProblemStatusEnum status;

    @Valid
    @Schema(name = "限制")
    Limit limit;

    @NotNull
    @NoNullElement
    @Schema(name = "允许的作答语言集合")
    List<LanguageEnum> languages;

    @Schema(name = "当题目类型为OI时的分数")
    private Integer oiScore;

    @Schema(name = "是否开启该题目的测试样例结果查看")
    boolean openCaseResult;

    @Schema(name = "团队设置")
    GroupSetting groupSetting;

    @NotNull
    @Schema(name = "申请公开的进度")
    ApplyPublicProgressEnum applyPublicProgress;

    @Valid
    @Schema(name = "远程评测设置")
    VJSetting vjSetting;

    @Valid
    @Schema(name = "特判程序或交互程序的设置")
    SPJSetting spjSetting;

    public static ProblemSetting defaultProblemSetting() {
        return ProblemSetting.builder()
                .type(ACM)
                .judgeMode(JudgeModeEnum.DEFAULT)
                .judgeCaseMode(JudgeCaseModeEnum.DEFAULT)
                .difficulty(EASY)
                .status(PRIVATE)
                .limit(Limit.defaultLimit())
                .languages(ALL)
                .oiScore(0)
                .openCaseResult(false)
                .groupSetting(defaultGroupSetting())
                .applyPublicProgress(NOT_APPLIED_YET)
                .vjSetting(VJSetting.defaultVJSetting())
                .spjSetting(defaultSPJSetting())
                .build();
    }

    public void validate() {
        if (isACMFormat() && oiScore != 0) {
            throw new MyException(ACM_FORMAT_DOES_NOT_NEED_SCORE, "The ACM format does not require score");
        }
    }

    public void setScore(int score) {
        if (isOIFormat()) {
            this.oiScore = score;
        }
    }

    public boolean isACMFormat() {
        return type == ACM;
    }

    public boolean isOIFormat() {
        return type == OI;
    }

    public void correctAndValidate() {
        groupSetting.correct();
        vjSetting.correct();

        groupSetting.validate();
        vjSetting.validate();
    }

}
