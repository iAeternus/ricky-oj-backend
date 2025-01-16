package org.ricky.core.tag.domain;

import lombok.RequiredArgsConstructor;
import org.ricky.common.exception.MyException;
import org.springframework.stereotype.Service;

import static org.ricky.common.exception.ErrorCodeEnum.TAG_WITH_NAME_ALREADY_EXISTS;
import static org.ricky.common.utils.CollectionUtils.mapOf;
import static org.ricky.common.utils.ValidationUtils.isNotBlank;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/5
 * @className TagDomainService
 * @desc
 */
@Service
@RequiredArgsConstructor
public class TagDomainService {

    private final TagRepository tagRepository;

    public void checkNameDuplication(String name) {
        if (isNotBlank(name) && tagRepository.existsByName(name)) {
            throw new MyException(TAG_WITH_NAME_ALREADY_EXISTS, "The tag name is occupied.", mapOf("name", name));
        }
    }
}
