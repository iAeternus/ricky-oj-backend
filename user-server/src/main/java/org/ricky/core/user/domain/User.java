package org.ricky.core.user.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ricky.common.context.UserContext;
import org.ricky.common.domain.AggregateRoot;
import org.ricky.common.domain.UploadedFile;
import org.ricky.core.user.domain.data.UserData;
import org.ricky.core.user.domain.title.Title;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import static org.ricky.common.constants.CommonConstants.USER_COLLECTION;
import static org.ricky.common.constants.CommonConstants.USER_ID_PREFIX;
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

    @Schema(name = "昵称")
    private String nickname;

    @Schema(name = "密码")
    private String password;

    @Schema(name = "邮箱")
    private String email;

    @Schema(name = "手机号")
    private String mobile;

    @Schema(name = "用户状态")
    private UserStatusEnum status;

    @Schema(name = "用户数据")
    private UserData userData;

    @Schema(name = "是否能创建题目")
    private boolean canCreateProblem;

    @Schema(name = "是否能创建比赛")
    private boolean canCreateContest;

    @Schema(name = "学校")
    private String school;

    @Schema(name = "专业")
    private String course;

    @Schema(name = "学号")
    private String idNumber;

    @Schema(name = "性别")
    private GenderEnum gender;

    @Schema(name = "真实姓名")
    private String realName;

    @Schema(name = "头像")
    private UploadedFile avatar;

    @Schema(name = "个性介绍")
    private String signature;

    @Schema(name = "头衔、称号")
    private Title title;

    @Schema(name = "cf的username")
    private String cfUsername;

    @Schema(name = "github地址")
    private String github;

    @Schema(name = "博客地址")
    private String blog;

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
    }

    public void activate(UserContext userContext) {
        if (status == ENABLE) {
            return;
        }

        status = ENABLE;
        addOpsLog("启用", userContext);
    }

    public void deactivate(UserContext userContext) {
        if (status == DISABLE) {
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
        return this.avatar != null ? this.avatar.getFileUrl() : null;
    }

}
