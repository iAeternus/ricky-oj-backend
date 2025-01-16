package org.ricky.core.oss.alter.command;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Command;
import org.ricky.common.exception.MyException;
import org.ricky.core.oss.domain.OssTokenRequestTypeEnum;
import org.ricky.common.validation.id.Id;

import static org.ricky.common.constants.CommonConstants.*;
import static org.ricky.common.exception.MyException.requestValidationException;
import static org.ricky.common.utils.ValidationUtils.isBlank;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/15
 * @className RequestOssTokenCommand
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestOssTokenCommand implements Command {

    @Id(prefix = PROBLEM_ID_PREFIX)
    String problemId;

    @Id(prefix = CASE_GROUP_ID_PREFIX)
    String caseGroupId;

    @Id(prefix = CASE_ID_PREFIX)
    String caseId;

    @Id(prefix = USER_ID_PREFIX)
    String userId;

    @NotNull
    OssTokenRequestTypeEnum type;

    @Override
    public void correctAndValidate() {
        switch (type) {
            case TEST_CASE_INPUT_EDIT, TEST_CASE_OUTPUT_EDIT -> {
                if (isBlank(problemId) || isBlank(caseGroupId) || isBlank(caseId)) {
                    throw throwNotValidRequestException();
                }
            }
            case USER_INFO -> {
                if (isBlank(userId)) {
                    throw throwNotValidRequestException();
                }
            }
        }
    }

    public String folder() {
        return switch (type) {
            case TEST_CASE_INPUT_EDIT -> problemId + "/" + caseGroupId + "/" + caseId + "/_INPUT_EDIT";
            case TEST_CASE_OUTPUT_EDIT -> problemId + "/" + caseGroupId + "/" + caseId + "/_OUTPUT_EDIT";
            case USER_INFO -> userId + "/_USER_INFO";
        };
    }

    private MyException throwNotValidRequestException() {
        return requestValidationException("ossRequestType", type);
    }

}
