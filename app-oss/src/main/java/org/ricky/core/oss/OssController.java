package org.ricky.core.oss;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ricky.common.context.UserContext;
import org.ricky.core.oss.alter.OssAlterationService;
import org.ricky.core.oss.alter.command.RequestOssTokenCommand;
import org.ricky.core.oss.domain.OssToken;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/15
 * @className OssController
 * @desc
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/oss")
public class OssController {

    private final OssAlterationService ossAlterationService;

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "生成OSS临时访问凭证")
    @PostMapping("/oss-token-requisitions")
    public OssToken generateOssToken(@RequestBody @Valid RequestOssTokenCommand command,
                                     @AuthenticationPrincipal UserContext userContext) {
        return ossAlterationService.generateOssToken(command, userContext);
    }

    // TODO 文件上传、修改、删除

}
