package org.ricky.management;

import static org.ricky.common.utils.SnowflakeIdGenerator.newSnowflakeId;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/26
 * @className SystemManager
 * @desc 暂定系统管理员只有一人
 */
public class SystemManager {

    public static final String ROJ_USER_UID = "USR000000000000000001";
    public static final String ADMIN_NICKNAME = "Ricky";
    public static final String ADMIN_INIT_MOBILE = "15111111111";
    public static final String ADMIN_INIT_PASSWORD = "11111111";

    public static String baseId(String prefix) {
        return prefix + "000000000000000001";
    }

    public static String newId(String prefix) {
        return prefix + newSnowflakeId();
    }

}
