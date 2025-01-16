package org.ricky.core.verification.domain;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/9/23
 * @className VerificationCodeSender
 * @desc 验证码发送器
 */
public interface VerificationCodeSender {

    /**
     * 发送验证码
     *
     * @param code 验证码
     */
    void send(VerificationCode code);

}
