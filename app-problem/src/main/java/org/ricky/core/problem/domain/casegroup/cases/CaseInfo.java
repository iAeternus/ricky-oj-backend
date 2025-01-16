package org.ricky.core.problem.domain.casegroup.cases;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/4
 * @className CaseInfo
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CaseInfo {

    String caseGroupId;
    String caseId;

}
