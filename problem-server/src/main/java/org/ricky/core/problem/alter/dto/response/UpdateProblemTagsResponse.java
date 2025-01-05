package org.ricky.core.problem.alter.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Response;

import java.util.List;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/5
 * @className UpdateProblemTagsResponse
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateProblemTagsResponse implements Response {

    @Schema(name = "添加的标签")
    List<String> added;

    @Schema(name = "删除的标签")
    List<String> deleted;

    public static UpdateProblemTagsResponse noChange() {
        return UpdateProblemTagsResponse.builder()
                .added(List.of())
                .deleted(List.of())
                .build();
    }

}
