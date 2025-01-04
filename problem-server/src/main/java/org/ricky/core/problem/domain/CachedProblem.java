package org.ricky.core.problem.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.ValueObject;

import java.util.List;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/2
 * @className CachedProblem
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CachedProblem implements ValueObject {

    String customId;
    String title;
    String author;
    String description;
    String inputFormat;
    String outputFormat;
    List<String> inputCases;
    List<String> outputCases;
    String hint;

}
