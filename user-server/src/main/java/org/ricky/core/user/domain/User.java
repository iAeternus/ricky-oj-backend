package org.ricky.core.user.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.ricky.common.context.UserContext;
import org.ricky.common.domain.AggregateRoot;
import org.ricky.common.domain.UploadedFile;
import org.ricky.common.exception.MyException;
import org.ricky.core.user.domain.data.UserData;
import org.ricky.core.user.domain.title.Title;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

import static java.time.LocalDate.now;
import static org.ricky.common.constants.CommonConstants.USER_COLLECTION;
import static org.ricky.common.constants.CommonConstants.USER_ID_PREFIX;
import static org.ricky.common.exception.ErrorCodeEnum.USER_ALREADY_DEACTIVATED;
import static org.ricky.common.exception.ErrorCodeEnum.USER_ALREADY_LOCKED;
import static org.ricky.common.utils.CollectionUtils.mapOf;
import static org.ricky.common.utils.SnowflakeIdGenerator.newSnowflakeId;
import static org.ricky.core.user.domain.UserStatusEnum.DISABLE;
import static org.ricky.core.user.domain.UserStatusEnum.ENABLE;
import static org.ricky.core.user.domain.data.UserData.defaultStudentData;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/27
 * @className User
 * @desc 用户
 */
@Getter
@Document(USER_COLLECTION)
@TypeAlias(USER_COLLECTION)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends AggregateRoot {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户状态
     */
    private UserStatusEnum status;

    /**
     * 用户数据
     */
    private UserData userData;

    /**
     * 是否能创建题目
     */
    private boolean canCreateProblem;

    /**
     * 是否能创建比赛
     */
    private boolean canCreateContest;

    /**
     * 学校
     */
    private String school;

    /**
     * 专业
     */
    private String course;

    /**
     * 学号
     */
    private String idNumber;

    /**
     * 性别
     */
    private GenderEnum gender;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 头像
     */
    private UploadedFile avatar;

    /**
     * 个性介绍
     */
    private String signature;

    /**
     * 头衔、称号
     */
    private Title title;

    /**
     * cf的username
     */
    private String cfUsername;

    /**
     * github地址
     */
    private String github;

    /**
     * 博客地址
     */
    private String blog;

    /**
     * 登录失败次数
     */
    private FailedLoginCount failedLoginCount; // TODO NPE

    public User(String nickname, String password, String email, String mobile, UserContext userContext) {
        super(newStudentId(), userContext);
        init(nickname, password, email, mobile);
        addOpsLog("新建学生", userContext);
    }

    public static String newStudentId() {
        return USER_ID_PREFIX + newSnowflakeId();
    }

    private void init(String nickname, String password, String email, String mobile) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
        this.status = ENABLE;
        this.userData = defaultStudentData();
        this.canCreateProblem = false;
        this.canCreateContest = false;
        this.failedLoginCount = FailedLoginCount.init();
    }

    public boolean isActivate() {
        return status == ENABLE;
    }

    public boolean isDeactivate() {
        return status == DISABLE;
    }

    public void activate(UserContext userContext) {
        if (isActivate()) {
            return;
        }

        status = ENABLE;
        addOpsLog("启用", userContext);
    }

    public void deactivate(UserContext userContext) {
        if (isDeactivate()) {
            return;
        }

        status = DISABLE;
        addOpsLog("禁用", userContext);
    }

    public void updateAvatar(UploadedFile avatar, UserContext userContext) {
        this.avatar = avatar;
        addOpsLog("更新头像", userContext);
    }

    public void deleteAvatar(UserContext userContext) {
        this.avatar = null;
        addOpsLog("删除头像", userContext);
    }

    private String avatarImageUrl() {
        return avatar != null ? avatar.getFileUrl() : null;
    }

    public void recordFailedLogin() {
        failedLoginCount.recordFailedLogin();
    }

    public void checkActive() {
        if (failedLoginCount.isLocked()) {
            throw new MyException(USER_ALREADY_LOCKED, "当前用户已经被锁定，次日零点系统将自动解锁。", mapOf("userId", this.getId()));
        }

        if (isDeactivate()) {
            throw new MyException(USER_ALREADY_DEACTIVATED, "当前用户已经被禁用。", mapOf("userId", this.getId()));
        }
    }

    /**
     * 登录失败计数
     */
    @Getter
    @Builder
    @EqualsAndHashCode
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class FailedLoginCount {

        private static final int MAX_ALLOWED_FAILED_LOGIN_PER_DAY = 30;

        private LocalDate date;
        private int count;

        public static FailedLoginCount init() {
            return FailedLoginCount.builder()
                    .date(now())
                    .count(0)
                    .build();
        }

        private void recordFailedLogin() {
            LocalDate now = now();
            if (now.equals(date)) {
                count++;
            } else {
                this.date = now;
                this.count = 0;
            }
        }

        private boolean isLocked() {
            return now().equals(date) && this.count >= MAX_ALLOWED_FAILED_LOGIN_PER_DAY;
        }
    }

}
