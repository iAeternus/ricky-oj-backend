package org.ricky.common.context;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.ricky.common.exception.MyException;
import org.ricky.common.utils.ValidationUtils;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.ricky.common.context.RoleEnum.*;
import static org.ricky.common.exception.ErrorCodeEnum.WRONG_USER;
import static org.ricky.common.exception.MyException.authenticationException;
import static org.ricky.common.utils.CollectionUtils.mapOf;
import static org.ricky.common.utils.ValidationUtils.*;

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

    public boolean isTeacher() {
        if (!internalIsLoggedIn()) {
            return false;
        }
        return internalIsRole(TEACHER);
    }

    public boolean isStudent() {
        if (!internalIsLoggedIn()) {
            return false;
        }
        return internalIsRole(STUDENT);
    }

    public void checkIsLoggedIn() {
        internalCheckLoggedIn();
    }

    public void checkIsSystemAdmin() {
        internalCheckLoggedIn();
        internalCheckRole(SYSTEM_ADMIN);
    }

    public void checkIsTeacher() {
        internalCheckLoggedIn();
        internalCheckRole(TEACHER);
    }

    public void checkIsStudent() {
        internalCheckLoggedIn();
        internalCheckRole(STUDENT);
    }

    public boolean isLoggedInFor(String userId) {
        requireNonBlank(userId, "User ID must not be blank.");

        if (!internalIsLoggedIn()) {
            return false;
        }
        return isUserFor(userId);
    }

    public boolean isSystemAdminFor(String userId) {
        requireNonBlank(userId, "User ID must not be blank.");

        if (!internalIsLoggedIn() || isWrongUserFor(userId)) {
            return false;
        }
        return internalIsRole(SYSTEM_ADMIN);
    }

    public boolean isTeacherFor(String userId) {
        requireNonBlank(userId, "User ID must not be blank.");

        if (!internalIsLoggedIn() || isWrongUserFor(userId)) {
            return false;
        }
        return internalIsRole(TEACHER);
    }

    public boolean isStudentFor(String userId) {
        requireNonBlank(userId, "User ID must not be blank.");

        if (!internalIsLoggedIn() || isWrongUserFor(userId)) {
            return false;
        }
        return internalIsRole(STUDENT);
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

    public void checkIsTeacherFor(String userId) {
        requireNonBlank(userId, "User ID must not be blank.");

        internalCheckLoggedIn();
        internalCheckUserFor(userId);
        internalCheckRole(TEACHER);
    }

    public void checkIsStudentFor(String userId) {
        requireNonBlank(userId, "User ID must not be blank.");

        internalCheckLoggedIn();
        internalCheckUserFor(userId);
        internalCheckRole(STUDENT);
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

    private boolean isUserFor(String userId) {
        return ValidationUtils.equals(this.userId, userId);
    }

    private boolean isWrongUserFor(String userId) {
        return notEquals(this.userId, userId);
    }

    private void internalCheckUserFor(String userId) {
        if (isWrongUserFor(userId)) {
            throw new MyException(WRONG_USER, "Wrong user.",
                    mapOf("Expected User ID", this.getUserId(), "Actual User ID", userId));
        }
    }

    @Override
    public String toString() {
        return "UserContext[" + this.userId + "]";
    }

}