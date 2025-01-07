package org.ricky.common.context;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.ricky.common.exception.MyException;
import org.ricky.common.utils.ValidationUtils;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.ricky.common.context.RoleEnum.SYSTEM_ADMIN;
import static org.ricky.common.context.RoleEnum.USER;
import static org.ricky.common.exception.ErrorCodeEnum.WRONG_USER;
import static org.ricky.common.exception.MyException.authenticationException;
import static org.ricky.common.utils.CollectionUtils.mapOf;
import static org.ricky.common.utils.ValidationUtils.*;
import static org.ricky.management.SystemManager.ADMIN_NICKNAME;
import static org.ricky.management.SystemManager.ROJ_USER_UID;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/26
 * @className UserContext
 * @desc
 */
@Getter
@EqualsAndHashCode
public class UserContext {

    /**
     * 无信息用户
     */
    public static final UserContext NOUSER = new UserContext(null, null, null);

    /**
     * 匿名用户
     */
    public static final UserContext ANONYMOUS_USER = NOUSER;

    /**
     * 系统管理员用户
     */
    public static final UserContext ADMIN_USER = new UserContext(ROJ_USER_UID, ADMIN_NICKNAME, SYSTEM_ADMIN);

    @Schema(name = "用户ID")
    private final String userId;

    @Schema(name = "昵称")
    private final String nickname;

    @Schema(name = "用户身份")
    private final RoleEnum role;

    private UserContext(String userId, String nickname, RoleEnum role) {
        this.userId = userId;
        this.nickname = nickname;
        this.role = role;
    }

    public static UserContext createUser(String userId, String nickname, RoleEnum role) {
        requireNonBlank(userId, "User ID must not be blank.");
        requireNonBlank(nickname, "Nickname must not be blank.");
        requireNonNull(role, "Role must not be null.");

        return new UserContext(userId, nickname, role);
    }

    public boolean isLoggedIn() {
        return internalIsLoggedIn();
    }

    public boolean isSystemAdmin() {
        if (!internalIsLoggedIn()) {
            return false;
        }
        return internalIsRole(SYSTEM_ADMIN);
    }

    public boolean isUser() {
        if (!internalIsLoggedIn()) {
            return false;
        }
        return internalIsRole(USER);
    }

    public void checkIsLoggedIn() {
        internalCheckLoggedIn();
    }

    public void checkIsSystemAdmin() {
        internalCheckLoggedIn();
        internalCheckRole(SYSTEM_ADMIN);
    }

    public void checkIsUser() {
        internalCheckLoggedIn();
        internalCheckRole(USER);
    }

    public boolean isLoggedInFor(String userId) {
        requireNonBlank(userId, "User ID must not be blank.");

        if (!internalIsLoggedIn()) {
            return false;
        }
        return internalIsUserFor(userId);
    }

    public boolean isSystemAdminFor(String userId) {
        requireNonBlank(userId, "User ID must not be blank.");

        if (!internalIsLoggedIn() || internalIsWrongUserFor(userId)) {
            return false;
        }
        return internalIsRole(SYSTEM_ADMIN);
    }

    public boolean isUserFor(String userId) {
        requireNonBlank(userId, "User ID must not be blank.");

        if (!internalIsLoggedIn() || internalIsWrongUserFor(userId)) {
            return false;
        }
        return internalIsRole(USER);
    }

    public void checkIsLoggedInFor(String userId) {
        requireNonBlank(userId, "User ID must not be blank.");

        internalCheckLoggedIn();
        internalCheckUserFor(userId);
    }

    public void checkIsSystemAdminFor(String userId) {
        requireNonBlank(userId, "User ID must not be blank.");

        internalCheckLoggedIn();
        internalCheckUserFor(userId);
        internalCheckRole(SYSTEM_ADMIN);
    }

    public void checkIsUserFor(String userId) {
        requireNonBlank(userId, "User ID must not be blank.");

        internalCheckLoggedIn();
        internalCheckUserFor(userId);
        internalCheckRole(USER);
    }

    private boolean internalIsLoggedIn() {
        return isNotBlank(userId) && nonNull(role);
    }

    private boolean internalIsRole(RoleEnum role) {
        return this.role == role;
    }

    private void internalCheckLoggedIn() {
        if (!internalIsLoggedIn()) {
            throw authenticationException();
        }
    }

    private void internalCheckRole(RoleEnum role) {
        if (!internalIsRole(role)) {
            throw authenticationException();
        }
    }

    private boolean internalIsUserFor(String userId) {
        return ValidationUtils.equals(this.userId, userId);
    }

    private boolean internalIsWrongUserFor(String userId) {
        return notEquals(this.userId, userId);
    }

    private void internalCheckUserFor(String userId) {
        if (internalIsWrongUserFor(userId)) {
            throw new MyException(WRONG_USER, "Wrong user.",
                    mapOf("Expected User ID", this.getUserId(), "Actual User ID", userId));
        }
    }

    @Override
    public String toString() {
        return "UserContext[" + this.userId + "]";
    }

}
