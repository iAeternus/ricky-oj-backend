package org.ricky.core.problem.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.domain.AggregateRoot;
import org.ricky.common.exception.MyException;
import org.ricky.common.utils.ValidationUtils;
import org.ricky.core.problem.domain.casegroup.CaseGroupInfo;
import org.ricky.core.problem.domain.casegroup.cases.CaseInfo;
import org.ricky.core.problem.domain.event.ProblemCaseDeletedEvent;
import org.ricky.core.problem.domain.event.ProblemCaseGroupDeletedEvent;
import org.ricky.core.problem.domain.event.ProblemDeletedEvent;
import org.ricky.core.problem.domain.setting.ProblemSetting;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.util.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static org.ricky.common.constants.CommonConstants.PROBLEM_COLLECTION;
import static org.ricky.common.constants.CommonConstants.PROBLEM_ID_PREFIX;
import static org.ricky.common.exception.ErrorCodeEnum.PROBLEM_ALREADY_UPDATED;
import static org.ricky.common.utils.CollectionUtils.mapOf;
import static org.ricky.common.utils.SnowflakeIdGenerator.newSnowflakeId;
import static org.ricky.common.utils.ValidationUtils.isNotEmpty;
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

    /**
     * 题目的自定义ID 例如（HOJ-1000）
     */
    private String customId;

    /**
     * 标题
     */
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 题目描述
     */
    private String description;

    /**
     * 输入格式
     */
    private String inputFormat;

    /**
     * 输出格式
     */
    private String outputFormat;

    /**
     * 输入样例
     */
    private List<String> inputCases;

    /**
     * 输出样例
     */
    private List<String> outputCases;

    /**
     * 备注
     */
    private String hint;

    /**
     * 题目设置
     */
    private ProblemSetting setting;

    /**
     * 题目设置的版本号，用于实现乐观锁
     */
    private String version;

    /**
     * 题目标签ID集合
     */
    private List<String> tags;

    public Problem(String customId, String title, String author, String description, String inputFormat, String outputFormat,
                   List<String> inputCases, List<String> outputCases, String hint, UserContext userContext) {
        super(newProblemId(), userContext);
        init(customId, title, author, description, inputFormat, outputFormat, inputCases, outputCases, hint, defaultProblemSetting());
        addOpsLog("新建题目", userContext);
    }

    public static String newProblemId() {
        return PROBLEM_ID_PREFIX + newSnowflakeId();
    }

    public void updateSetting(ProblemSetting newSetting, String version, UserContext userContext) {
        if (!ValidationUtils.equals(this.getVersion(), version)) {
            throw new MyException(PROBLEM_ALREADY_UPDATED, "更新失败，题目已经在别处被更新，请刷新页面后重新编辑。", mapOf("problemId", this.getId()));
        }

        ProblemSettingContext newContext = newSetting.context();
        correctAndValidate(newContext);
        ProblemSettingContext oldContext = setting.context();

        checkCaseGroupsAndCasesDeletion(oldContext, newContext, userContext);

        doUpdateSetting(newSetting);
        addOpsLog("变更题目设置", userContext);
    }

    /**
     * @return first-addedTags second->deletedTags
     */
    public Pair<List<String>, List<String>> updateTags(List<String> newTags, UserContext userContext) {
        Set<String> oldTags = new HashSet<>(tags);
        Set<String> newlyTags = new HashSet<>(newTags);
        newTags.forEach(oldTags::remove); // deleted
        tags.forEach(newlyTags::remove); // added
        doUpdateTags(newTags);
        addOpsLog("变更题目标签", userContext);
        return Pair.of(newlyTags.stream().collect(toImmutableList()), oldTags.stream().collect(toImmutableList()));
    }

    public void deleteTag(String tagId) {
        tags.remove(tagId);
    }

    public void onDelete(UserContext userContext) {
        raiseEvent(new ProblemDeletedEvent(getId(), userContext));
    }

    private void init(String problemId, String title, String author, String description, String inputFormat, String outputFormat,
                      List<String> inputCases, List<String> outputCases, String hint, ProblemSetting setting) {
        this.customId = problemId;
        this.title = title;
        this.author = author;
        this.description = description;
        this.inputFormat = inputFormat;
        this.outputFormat = outputFormat;
        this.inputCases = inputCases;
        this.outputCases = outputCases;
        this.hint = hint;
        correctAndValidate(setting.context());
        doUpdateSetting(setting);
        this.tags = List.of();
    }

    private String newVersion() {
        return String.valueOf(System.currentTimeMillis());
    }

    private void correctAndValidate(ProblemSettingContext context) {
        try {
            context.correctAndValidate();
        } catch (MyException ex) {
            ex.addData("problemId", this.getId());
            throw ex;
        }
    }

    private void doUpdateSetting(ProblemSetting newSetting) {
        this.setting = newSetting;
        this.version = newVersion();
    }

    private void doUpdateTags(List<String> newTags) {
        this.tags = newTags;
    }

    private void checkCaseGroupsAndCasesDeletion(ProblemSettingContext oldContext, ProblemSettingContext newContext, UserContext userContext) {
        Set<CaseGroupInfo> deletedCaseGroups = oldContext.calculateDeletedCaseGroups(newContext);
        Set<String> deletedCaseGroupIds = deletedCaseGroups.stream().map(CaseGroupInfo::getCaseGroupId).collect(toImmutableSet());
        if (isNotEmpty(deletedCaseGroupIds)) {
            raiseEvent(new ProblemCaseGroupDeletedEvent(getId(), deletedCaseGroups, userContext));
        }

        Set<CaseInfo> deletedCaseInfos = oldContext.calculateDeletedCaseInfos(newContext, deletedCaseGroupIds);
        if (isNotEmpty(deletedCaseInfos)) {
            raiseEvent(new ProblemCaseDeletedEvent(getId(), deletedCaseInfos, userContext));
        }
    }

}
