package org.ricky.core.judge.domain.submit;

import lombok.Getter;
import org.ricky.common.exception.ErrorCodeEnum;
import org.ricky.common.exception.MyException;

import static org.ricky.common.exception.ErrorCodeEnum.INVALID_SUBMIT_TYPE;
import static org.ricky.common.utils.CollectionUtils.mapOf;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/8
 * @className SubmitTypeEnum
 * @desc 提交类型
 */
@Getter
public enum SubmitTypeEnum {

    CONTEXT((short) 0),
    GENERAL((short) 1),
    TEST((short) 2),
    ;

    final short key;

    SubmitTypeEnum(short key) {
        this.key = key;
    }

    public static SubmitTypeEnum of(short key) {
        for (SubmitTypeEnum type : values()) {
            if(type.getKey() == key) {
                return type;
            }
        }
        throw new MyException(INVALID_SUBMIT_TYPE, "Invalid submit type", mapOf("key", key));
    }

}
