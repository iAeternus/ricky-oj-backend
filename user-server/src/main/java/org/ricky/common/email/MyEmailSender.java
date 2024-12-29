package org.ricky.common.email;


/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/29
 * @className AliyunSmsSender
 * @desc
 */
public interface MyEmailSender {

    /**
     * 发送邮箱验证码
     *
     * @param email 邮箱
     * @param code  验证码
     */
    void sendVerificationCode(String email, String code);

}
