package org.ricky.common.sms;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/29
 * @className MySmsSender
 * @desc
 */
public interface MySmsSender {

    /**
     * 发送短信验证码
     *
     * @param mobile 手机号
     * @param code   验证码
     * @return true=发送成功 false=发送失败
     */
    boolean sendVerificationCode(String mobile, String code);

}
