package org.ricky.core.verification.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.domain.AggregateRoot;
import org.ricky.common.exception.MyException;
import org.ricky.common.utils.ValidationUtils;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.ricky.common.constants.CommonConstants.NO_USER_ID;
import static org.ricky.common.constants.CommonConstants.VERIFICATION_COLLECTION;
import static org.ricky.common.exception.ErrorCodeEnum.VERIFICATION_CODE_COUNT_OVERFLOW;
import static org.ricky.common.utils.SnowflakeIdGenerator.newSnowflakeId;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/9/23
 * @className VerificationCode
 * @desc 验证码
 */
@Getter
@Document(VERIFICATION_COLLECTION)
@TypeAlias(VERIFICATION_COLLECTION)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VerificationCode extends AggregateRoot {

    /**
     * 邮箱或手机号
     */
    private String mobileOrEmail;

    /**
     * 6位数验证码
     */
    private String code;

    /**
     * 验证码用于的类型
     */
    private VerificationCodeTypeEnum type;

    /**
     * 已经使用的次数，使用次数不能超过3次
     */
    private int usedCount;

    public VerificationCode(String mobileOrEmail, VerificationCodeTypeEnum type, String userId, UserContext userContext) {
        super(newVerificationCodeId(), isNotBlank(userId) ? userId : NO_USER_ID, userContext);
        this.mobileOrEmail = mobileOrEmail;
        this.code = randomNumeric(6);
        this.type = type;
        this.usedCount = 0;
    }

    public static String newVerificationCodeId() {
        return "VRC" + newSnowflakeId();
    }

    /**
     * 使用验证码，使用次数+1
     */
    public void use() {
        if (usedCount >= 3) {
            throw new MyException(VERIFICATION_CODE_COUNT_OVERFLOW, "验证码已超过可使用次数。");
        }

        this.usedCount++;
    }

}
