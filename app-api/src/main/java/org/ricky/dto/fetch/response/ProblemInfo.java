package org.ricky.dto.fetch.response;

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
 * @date 2025/1/7
 * @className ProblemInfo
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProblemInfo implements Response {

    @Schema(name = "题目的自定义ID 例如（HOJ-1000）")
    String customId;

    @Schema(name = "标题")
    String title;

    @Schema(name = "作者")
    String author;

    @Schema(name = "题目描述")
    String description;

    @Schema(name = "输入格式")
    String inputFormat;

    @Schema(name = "输出格式")
    String outputFormat;

    @Schema(name = "输入样例")
    List<String> inputCases;

    @Schema(name = "输出样例")
    List<String> outputCases;

    @Schema(name = "备注")
    String hint;

}
