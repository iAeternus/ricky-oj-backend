package org.ricky.core.problem.domain;

import org.ricky.common.exception.MyException;
import org.ricky.core.problem.domain.casegroup.CaseGroup;
import org.ricky.core.problem.domain.casegroup.CaseGroupInfo;
import org.ricky.core.problem.domain.casegroup.cases.CaseInfo;
import org.ricky.core.problem.domain.setting.ProblemSetting;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static org.ricky.common.exception.ErrorCodeEnum.CASE_GROUP_ID_DUPLICATED;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/4
 * @className ProblemSettingContext
 * @desc
 */
public class ProblemSettingContext {

    private final ProblemSetting problemSetting;

    private final Set<String> allCaseGroupIds;
    private final Map<String, CaseGroup> allCaseGroups;

    private final Map<String, CaseInfo> allCaseInfos;

    public ProblemSettingContext(ProblemSetting problemSetting) {
        this.problemSetting = problemSetting;

        Set<String> allCaseGroupIds = new HashSet<>();
        Map<String, CaseGroup> allCaseGroups = new HashMap<>();
        Map<String, CaseInfo> allCaseInfos = new HashMap<>();

        problemSetting.getCaseGroups().forEach(caseGroup -> {
            allCaseGroupIds.add(caseGroup.getId());
            allCaseGroups.put(caseGroup.getId(), caseGroup);
            caseGroup.getCases().forEach(caze -> {
                CaseInfo caseInfo = CaseInfo.builder()
                        .caseGroupId(caseGroup.getId())
                        .caseId(caze.getId())
                        .build();
                allCaseInfos.put(caze.getId(), caseInfo);
            });
        });

        this.allCaseGroupIds = Set.copyOf(allCaseGroupIds);
        this.allCaseGroups = Map.copyOf(allCaseGroups);
        this.allCaseInfos = Map.copyOf(allCaseInfos);
    }

    public void correctAndValidate() {
        problemSetting.getVjSetting().correct();

        validateCaseGroupIdsNoDuplication();

        problemSetting.getVjSetting().validate();
        problemSetting.getCaseGroups().forEach(CaseGroup::validate);
    }

    public Set<CaseGroupInfo> calculateDeletedCaseGroups(ProblemSettingContext newContext) {
        Set<String> oldCaseGroupIds = new HashSet<>(allCaseGroupIds);
        oldCaseGroupIds.removeAll(newContext.allCaseGroupIds);
        return oldCaseGroupIds.stream().map(id -> allCaseGroups.get(id).toCaseGroupInfo()).collect(toImmutableSet());
    }

    public Set<CaseInfo> calculateDeletedCaseInfos(ProblemSettingContext newContext, Set<String> excludeCaseGroupIds) {
        Set<CaseInfo> oldCaseInfos = new HashSet<>(allCaseInfos.values());
        oldCaseInfos.removeAll(newContext.allCaseInfos.values());
        return oldCaseInfos.stream()
                .filter(caseInfo -> !excludeCaseGroupIds.contains(caseInfo.getCaseGroupId()))
                .collect(toImmutableSet());
    }

    private void validateCaseGroupIdsNoDuplication() {
        if (allCaseGroupIds.size() != problemSetting.getCaseGroups().size()) {
            throw new MyException(CASE_GROUP_ID_DUPLICATED, "题目下所有测试用例组名不能重复");
        }
    }

}
