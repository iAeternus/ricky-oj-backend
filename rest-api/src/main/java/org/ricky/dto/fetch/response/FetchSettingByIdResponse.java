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

import java.util.List;

import static org.ricky.common.exception.ErrorCodeEnum.PROBLEM_STATUS_FORBIDDEN;
import static org.ricky.dto.fetch.response.FetchSettingByIdResponse.ProblemStatusEnum.PRIVATE;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/7
 * @className FetchSettingByIdResponse
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FetchSettingByIdResponse implements Response {

    @Schema(name = "题目类型")
    ProblemTypeEnum type;

    @Schema(name = "评测模式")
    JudgeModeEnum judgeMode;

    @Schema(name = "评测样例模式")
    JudgeCaseModeEnum judgeCaseMode;

    @Schema(name = "题目难度")
    ProblemDifficultyEnum difficulty;

    @Schema(name = "题目状态")
    ProblemStatusEnum status;

    @Schema(name = "时间限制 ms")
    Double timeLimit;

    @Schema(name = "内存空间限制 MB")
    Double memoryLimit;

    @Schema(name = "栈空间限制 MB")
    Double stackLimit;

    @Schema(name = "允许的作答语言集合")
    List<LanguageEnum> languages;

    @Schema(name = "是否开启该题目的测试样例结果查看")
    Boolean openCaseResult;

    @Schema(name = "申请公开的进度")
    ApplyPublicProgressEnum applyPublicProgress;

    @Schema(name = "是否为vj判题")
    Boolean isRemote;

    @Schema(name = "题目来源（例如vj判题时HDU-1000的链接）")
    String source;

    @Schema(name = "特判程序或交互程序的代码")
    String spjCode;

    @Schema(name = "特判程序或交互程序的语言")
    LanguageEnum spjLanguage;

    @Schema(name = "特判程序或交互程序的额外文件")
    UploadedFile userExtraFile;

    @Schema(name = "特判程序或交互程序的额外文件")
    UploadedFile judgeExtraFile;

    @Schema(name = "测试用例组集合")
    List<CaseGroup> caseGroups;

    @Schema(name = "答案集合")
    List<Answer> answers;

    public void checkStatus() {
        if (status == PRIVATE) {
            throw new MyException(PROBLEM_STATUS_FORBIDDEN, "The current problem cannot be submitted!");
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
