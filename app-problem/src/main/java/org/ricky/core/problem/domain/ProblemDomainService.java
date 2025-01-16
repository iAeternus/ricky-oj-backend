package org.ricky.core.problem.domain;

import lombok.RequiredArgsConstructor;
import org.ricky.common.exception.MyException;
import org.springframework.stereotype.Service;

import static org.ricky.common.exception.ErrorCodeEnum.PROBLEM_WITH_CUSTOM_ID_ALREADY_EXISTS;
import static org.ricky.common.utils.CollectionUtils.mapOf;
import static org.ricky.common.utils.ValidationUtils.isNotBlank;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/2
 * @className ProblemDomainService
 * @desc
 */
@Service
@RequiredArgsConstructor
public class ProblemDomainService {

    private final ProblemRepository problemRepository;

    public void checkCustomIdDuplication(String customId) {
        if (isNotBlank(customId) && problemRepository.cachedExistsByCustomId(customId)) {
            throw new MyException(PROBLEM_WITH_CUSTOM_ID_ALREADY_EXISTS, "The custom number is occupied.",
                    mapOf("customId", customId));
        }
    }
}
