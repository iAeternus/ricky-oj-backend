package org.ricky.core.tag;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.validation.id.Id;
import org.ricky.core.tag.alter.TagAlterationService;
import org.ricky.core.tag.alter.dto.command.CreateProblemTagCommand;
import org.ricky.core.tag.alter.dto.command.UpdateTagInfoCommand;
import org.ricky.core.tag.alter.dto.response.CreateProblemTagResponse;
import org.ricky.core.tag.fetch.TagFetchService;
import org.ricky.core.tag.fetch.dto.response.FetchAllResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.ricky.common.constants.CommonConstants.TAG_ID_PREFIX;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/5
 * @className TagController
 * @desc
 */
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "题目标签相关接口")
@RequestMapping(value = "/tag")
public class TagController {

    private final TagAlterationService tagAlterationService;
    private final TagFetchService tagFetchService;

    @PostMapping("/create")
    @Operation(summary = "创建题目标签")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateProblemTagResponse createProblemTag(@RequestBody @Valid CreateProblemTagCommand command,
                                                     @AuthenticationPrincipal UserContext userContext) {
        return tagAlterationService.createProblemTag(command, userContext);
    }

    @DeleteMapping("/{tagId}/remove")
    @Operation(summary = "删除题目标签")
    public void removeProblemTag(@PathVariable @Id(prefix = TAG_ID_PREFIX) String tagId,
                                 @AuthenticationPrincipal UserContext userContext) {
        tagAlterationService.removeProblemTag(tagId, userContext);
    }

    @PutMapping("/{tagId}/update")
    @Operation(summary = "修改标签信息")
    public void updateProblemTag(@PathVariable("tagId") @Id(prefix = TAG_ID_PREFIX) String tagId,
                                 @RequestBody @Valid UpdateTagInfoCommand command,
                                 @AuthenticationPrincipal UserContext userContext) {
        tagAlterationService.updateProblemTag(tagId, command, userContext);
    }

    @GetMapping("/fetch/all")
    @Operation(summary = "获取所有标签")
    public FetchAllResponse fetchAll(@AuthenticationPrincipal UserContext userContext) {
        return tagFetchService.fetchAll(userContext);
    }

}
