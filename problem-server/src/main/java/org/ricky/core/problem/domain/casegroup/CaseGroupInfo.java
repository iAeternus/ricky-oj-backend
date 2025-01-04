package org.ricky.core.problem.domain.casegroup;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.core.problem.domain.casegroup.cases.CaseInfo;

import java.util.Set;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/4
 * @className CaseGroupInfo
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CaseGroupInfo {

    String caseGroupId;
    Set<CaseInfo> caseInfos;

}
