package org.ricky.core.judger.fetch;

import lombok.RequiredArgsConstructor;
import org.ricky.core.judger.domain.StartupRunner;
import org.ricky.core.judger.fetch.dto.response.FetchJudgerInfoResponse;
import org.springframework.stereotype.Service;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/7
 * @className JudgerFetchService
 * @desc
 */
@Service
@RequiredArgsConstructor
public class JudgerFetchService {

    private final StartupRunner startupRunner;

    public FetchJudgerInfoResponse fetchJudgerInfo() {
        return FetchJudgerInfoResponse.builder()
                .info(startupRunner.fetchJudgerInfo())
                .build();
    }
}
