package org.ricky.core.problem.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.domain.AggregateRoot;
import org.ricky.core.problem.domain.answer.Answer;
import org.ricky.core.problem.domain.setting.ProblemSetting;
import org.ricky.core.problem.domain.casegroup.CaseGroup;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import static org.ricky.common.constants.CommonConstants.PROBLEM_COLLECTION;
import static org.ricky.common.constants.CommonConstants.PROBLEM_ID_PREFIX;
import static org.ricky.common.utils.CollectionUtils.mapOf;
import static org.ricky.common.utils.SnowflakeIdGenerator.newSnowflakeId;
import static org.ricky.core.problem.domain.setting.ProblemSetting.defaultProblemSetting;


/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/26
 * @className Problem
 * @desc 题目
 */
@Getter
@Document(PROBLEM_COLLECTION)
@TypeAlias(PROBLEM_COLLECTION)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Problem extends AggregateRoot {

    @Schema(name = "题目的自定义ID 例如（HOJ-1000）")
    private String customId;

    @Schema(name = "标题")
    private String title;

    @Schema(name = "作者")
    private String author;

    @Schema(name = "题目描述")
    private String description;

    @Schema(name = "输入格式")
    private String inputFormat;

    @Schema(name = "输出格式")
    private String outputFormat;

    @Schema(name = "输入样例")
    private List<String> inputCases;

    @Schema(name = "输出样例")
    private List<String> outputCases;

    @Schema(name = "备注")
    private String hint;

    @Schema(name = "题目设置")
    private ProblemSetting setting;

    @Schema(name = "测试用例组集合")
    private List<CaseGroup> caseGroups;

    @Schema(name = "题目标签组ID集合")
    private List<String> tagGroups;

    @Schema(name = "答案集合")
    private List<Answer> answers;

    @Schema(name = "题目测试数据的版本号，用于实现乐观锁")
    private String caseVersion;

    public Problem(String customId, String title, String author, String description, String inputFormat, String outputFormat,
                   List<String> inputCases, List<String> outputCases, String hint, UserContext userContext) {
        super(newProblemId(), userContext);
        init(customId, title, author, description, inputFormat, outputFormat, inputCases, outputCases, hint);
        correctAndValidate();
        addOpsLog("新建题目", userContext);
    }

    public static String newProblemId() {
        return PROBLEM_ID_PREFIX + newSnowflakeId();
    }

    private void init(String problemId, String title, String author, String description, String inputFormat, String outputFormat,
                      List<String> inputCases, List<String> outputCases, String hint) {
        this.customId = problemId;
        this.title = title;
        this.author = author;
        this.description = description;
        this.inputFormat = inputFormat;
        this.outputFormat = outputFormat;
        this.inputCases = inputCases;
        this.outputCases = outputCases;
        this.hint = hint;
        this.setting = defaultProblemSetting();
        this.caseGroups = List.of();
        this.tagGroups = List.of();
        this.answers = List.of();
        this.caseVersion = newCaseVersion();
    }

    private String newCaseVersion() {
        return String.valueOf(System.currentTimeMillis());
    }

    private void correctAndValidate() {
        setting.correctAndValidate();
    }

}
