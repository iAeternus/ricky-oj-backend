package org.ricky.core.problem.alter.command;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Command;
import org.ricky.common.exception.MyException;
import org.ricky.common.validation.collection.NoNullElement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.ricky.common.exception.ErrorCodeEnum.TAGS_ALREADY_DUPLICATED;
import static org.ricky.common.utils.CollectionUtils.listToString;
import static org.ricky.common.utils.CollectionUtils.mapOf;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/5
 * @className UpdateProblemTagsCommand
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateProblemTagsCommand implements Command {

    @NotNull
    @NoNullElement
    List<String> tags;

    @Override
    public void correctAndValidate() {
        Set<String> distinctTags = new HashSet<>(tags);
        if (distinctTags.size() != tags.size()) {
            throw new MyException(TAGS_ALREADY_DUPLICATED, "Tags cannot be repeated.", mapOf("tags", listToString(tags)));
        }
    }
}
