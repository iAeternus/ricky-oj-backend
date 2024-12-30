package org.ricky.common.context;

import lombok.Getter;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/9/9
 * @className RoleEnum
 * @desc 用户身份
 */
@Getter
public enum RoleEnum {

    SYSTEM_ADMIN("系统管理员"),
    USER("用户"),
    ;

    final String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }

}
