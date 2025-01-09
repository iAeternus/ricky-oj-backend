package org.ricky.core.os;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ricky.core.os.fetch.OSFetchService;
import org.ricky.core.os.fetch.response.FetchOSConfigResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/7
 * @className OSController
 * @desc
 */
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "操作系统相关接口")
@RequestMapping(value = "/os")
public class OSController {

    private final OSFetchService osFetchService;

    @GetMapping("/config")
    @Operation(summary = "获取操作系统配置")
    public FetchOSConfigResponse fetchOSConfig() {
        return osFetchService.fetchOSConfig();
    }

}
