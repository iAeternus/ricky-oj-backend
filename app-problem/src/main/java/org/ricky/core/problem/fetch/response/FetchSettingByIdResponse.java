package org.ricky.core.problem.fetch.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.LanguageEnum;
import org.ricky.common.domain.UploadedFile;
import org.ricky.common.domain.marker.Response;
import org.ricky.core.problem.domain.answer.Answer;
import org.ricky.core.problem.domain.casegroup.CaseGroup;
import org.ricky.core.problem.domain.setting.*;

import java.util.List;

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
    Integer timeLimit;

    @Schema(name = "内存空间限制 MB")
    Integer memoryLimit;

    @Schema(name = "栈空间限制 MB")
    Integer stackLimit;

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

}
