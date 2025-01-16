package org.ricky.core.oss.alter;

import lombok.RequiredArgsConstructor;
import org.ricky.client.ProblemClient;
import org.ricky.common.context.UserContext;
import org.ricky.core.oss.alter.command.RequestOssTokenCommand;
import org.ricky.core.oss.domain.AliyunOssTokenGenerator;
import org.ricky.core.oss.domain.OssToken;
import org.ricky.common.ratelimit.RateLimiter;
import org.ricky.dto.fetch.response.ProblemSetting;
import org.springframework.stereotype.Service;

import static org.ricky.common.ratelimit.TPSConstants.EXTREMELY_LOW_TPS;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/15
 * @className OssAlterationService
 * @desc
 */
@Service
@RequiredArgsConstructor
public class OssAlterationService {

    private final RateLimiter rateLimiter;
    private final AliyunOssTokenGenerator ossTokenGenerator;
    private final ProblemClient problemClient;

    public OssToken generateOssToken(RequestOssTokenCommand command, UserContext userContext) {
        String userId = userContext.getUserId();

        switch (command.getType()) {
            case TEST_CASE_INPUT_EDIT -> {
                rateLimiter.applyFor(userId, "OssToken:TestCaseInputEdit", EXTREMELY_LOW_TPS);
                ProblemSetting problemSetting = problemClient.fetchSettingById(command.getProblemId());
                problemSetting.checkTestCases(command.getCaseGroupId(), command.getCaseId());
            }
            case TEST_CASE_OUTPUT_EDIT -> {
                rateLimiter.applyFor(userId, "OssToken:TestCaseOutputEdit", EXTREMELY_LOW_TPS);
                ProblemSetting problemSetting = problemClient.fetchSettingById(command.getProblemId());
                problemSetting.checkTestCases(command.getCaseGroupId(), command.getCaseId());
            }
            case USER_INFO -> rateLimiter.applyFor(userId, "OssToken:UserEdit", EXTREMELY_LOW_TPS);
            // TODO add here...
        }

        return ossTokenGenerator.generateOssToken(command.folder());
    }
}
