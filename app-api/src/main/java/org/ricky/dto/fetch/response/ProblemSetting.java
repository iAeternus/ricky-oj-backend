package org.ricky.dto.fetch.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.ricky.common.domain.LanguageEnum;
import org.ricky.common.domain.UploadedFile;
import org.ricky.common.domain.marker.Identified;
import org.ricky.common.domain.marker.Response;
import org.ricky.common.domain.marker.ValueObject;
import org.ricky.common.domain.program.Program;
import org.ricky.common.exception.MyException;
import org.ricky.common.properties.LanguageProperties;
import org.ricky.common.utils.ValidationUtils;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.ricky.common.exception.ErrorCodeEnum.CASE_ID_NOT_MATCH;
import static org.ricky.common.exception.ErrorCodeEnum.PROBLEM_STATUS_FORBIDDEN;
import static org.ricky.common.utils.CollectionUtils.mapOf;
import static org.ricky.common.utils.ValidationUtils.isBlank;
import static org.ricky.dto.fetch.response.ProblemSetting.ProblemStatusEnum.PRIVATE;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/7
 * @className ProblemSetting
 * @desc
 */
@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProblemSetting implements Response {

    @Schema(name = "题目类型")
    private ProblemTypeEnum type;

    @Schema(name = "评测模式")
    private JudgeModeEnum judgeMode;

    @Schema(name = "评测样例模式")
    private JudgeCaseModeEnum judgeCaseMode;

    @Schema(name = "题目难度")
    private ProblemDifficultyEnum difficulty;

    @Schema(name = "题目状态")
    private ProblemStatusEnum status;

    @Schema(name = "时间限制 ms")
    private Integer timeLimit;

    @Schema(name = "内存空间限制 MB")
    private Integer memoryLimit;

    @Schema(name = "栈空间限制 MB")
    private Integer stackLimit;

    @Schema(name = "允许的作答语言集合")
    private List<LanguageEnum> languages;

    @Schema(name = "是否开启该题目的测试样例结果查看")
    private Boolean openCaseResult;

    @Schema(name = "申请公开的进度")
    private ApplyPublicProgressEnum applyPublicProgress;

    @Schema(name = "是否为vj判题")
    private Boolean isRemote;

    @Schema(name = "题目来源（例如vj判题时HDU-1000的链接）")
    private String source;

    @Schema(name = "特判程序或交互程序的代码")
    private String spjCode;

    @Schema(name = "特判程序或交互程序的语言")
    private LanguageEnum spjLanguage;

    @Schema(name = "特判程序或交互程序的额外文件")
    private UploadedFile userExtraFile;

    @Schema(name = "特判程序或交互程序的额外文件")
    private UploadedFile judgeExtraFile;

    @Schema(name = "测试用例组集合")
    private List<CaseGroup> caseGroups;

    @Schema(name = "答案集合")
    private List<Answer> answers;

    public void checkStatus() {
        if (status == PRIVATE) {
            throw new MyException(PROBLEM_STATUS_FORBIDDEN, "The current problem cannot be submitted!");
        }
    }

    /**
     * c和c++为一倍的时间和空间，其它语言为2倍时间和空间
     */
    public void correctLimit(LanguageProperties languageConfig) {
        if (isBlank(languageConfig.getSrcName()) ||
                !languageConfig.getSrcName().endsWith(".c") && !languageConfig.getSrcName().endsWith(".cpp")) {
            timeLimit *= 2;
            memoryLimit *= 2;
        }
    }

    public void checkTestCases(String caseGroupId, String caseId) {
        boolean containsCaseGroupId = caseGroups.stream()
                .anyMatch(caseGroup -> ValidationUtils.equals(caseGroup.getId(), caseGroupId));
        boolean containsCaseId = caseGroups.stream()
                .flatMap(caseGroup -> caseGroup.getCases().stream().map(CaseGroup.Case::getId))
                .anyMatch(cazeId -> ValidationUtils.equals(cazeId, caseId));
        if(!containsCaseGroupId || !containsCaseId) {
            throw new MyException(CASE_ID_NOT_MATCH, "CaseId, CaseGroupId do not match.",
                    mapOf("caseGroupId", caseGroupId, "caseId", caseId));
        }
    }

    public enum ProblemTypeEnum {
        ACM,
        // 暂不支持OI赛制
        // OI,
        ;
    }

    public enum JudgeModeEnum {

        DEFAULT,
        SPJ,
        INTERACTIVE,
        ;

    }

    public enum JudgeCaseModeEnum {

        DEFAULT,
        SUBTASK_LOWEST,
        SUBTASK_AVERAGE,
        ERGODIC_WITHOUT_ERROR,
        ;

    }

    public enum ProblemDifficultyEnum {

        EASY,
        MEDIUM,
        HARD,
        IMPOSSIBLE,
        ;

    }

    public enum ProblemStatusEnum {

        PRIVATE,
        PUBLIC,
        IN_CONTEST,
        ;
    }

    public enum ApplyPublicProgressEnum {

        NOT_APPLIED_YET,
        APPLYING,
        PASSED,
        REJECTED,
        ;

    }

    @Getter
    @Builder
    @EqualsAndHashCode
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CaseGroup implements Identified {

        @Schema(name = "ID")
        private final String id;

        @Schema(name = "组名")
        private final String name;

        @Schema(name = "测试用例集合")
        private List<Case> cases;

        @Override
        public String getIdentifier() {
            return id;
        }

        @Getter
        @Builder
        @EqualsAndHashCode
        @AllArgsConstructor(access = AccessLevel.PRIVATE)
        public static class Case implements Identified {

            @Schema(name = "ID")
            private final String id;

            @Schema(name = "测试用例的输入文件")
            private UploadedFile input;

            @Schema(name = "测试用例的输出文件")
            private UploadedFile output;

            @Schema(name = "排序")
            private Integer seq;

            @Override
            public String getIdentifier() {
                return id;
            }
        }
    }

    @Value
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Answer implements ValueObject {

        @Schema(name = "是否启用")
        Boolean enable;

        @Schema(name = "程序")
        Program program;

    }

}
