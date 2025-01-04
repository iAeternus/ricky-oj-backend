package org.ricky.core.problem.alter.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Command;
import org.ricky.common.exception.MyException;
import org.ricky.common.validation.collection.NoNullElement;
import org.ricky.common.validation.id.Id;

import java.util.List;

import static org.ricky.common.constants.CommonConstants.*;
import static org.ricky.common.exception.ErrorCodeEnum.INPUT_OUTPUT_NOT_MATCH;
import static org.ricky.common.utils.CollectionUtils.mapOf;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/2
 * @className CreateProblemCommand
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateProblemCommand implements Command {

    @NotBlank
    String customId;

    @NotBlank
    @Size(max = MAX_GENERIC_NAME_LENGTH)
    String title;

    @NotBlank
    @Size(max = MAX_GENERIC_NAME_LENGTH)
    String author;

    @NotBlank
    @Size(max = MAX_GENERIC_TEXT_LENGTH)
    String description;

    @NotBlank
    @Size(max = MAX_GENERIC_TEXT_LENGTH)
    String inputFormat;

    @NotBlank
    @Size(max = MAX_GENERIC_TEXT_LENGTH)
    String outputFormat;

    @NotNull
    @NoNullElement
    @Size(max = MAX_CASES_SIZE)
    List<String> inputCases;

    @NotNull
    @NoNullElement
    @Size(max = MAX_CASES_SIZE)
    List<String> outputCases;

    @NotBlank
    @Size(max = MAX_GENERIC_TEXT_LENGTH)
    String hint;

    @Override
    public void correctAndValidate() {
        if(inputCases.size() != outputCases.size()) {
            throw new MyException(INPUT_OUTPUT_NOT_MATCH, "The input does not match the number of outputs.",
                    mapOf("inputSize", inputCases.size(), "outputSize", outputCases.size()));
        }
    }
}
