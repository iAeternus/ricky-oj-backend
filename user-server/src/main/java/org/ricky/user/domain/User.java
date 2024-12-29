package org.ricky.user.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ricky.common.context.RoleEnum;
import org.ricky.common.context.UserContext;
import org.ricky.common.domain.AggregateRoot;
import org.ricky.common.domain.UploadedFile;
import org.ricky.user.domain.data.UserData;
import org.ricky.user.domain.title.Title;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import static org.ricky.common.constants.CommonConstants.USER_COLLECTION;
import static org.ricky.common.constants.CommonConstants.USER_ID_PREFIX;
import static org.ricky.common.utils.SnowflakeIdGenerator.newSnowflakeId;

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

    @Schema(name = "邮箱")
    private String email;

    @Schema(name = "昵称")
    private String nickname;

    @Schema(name = "密码")
    private String password;

    @Schema(name = "用户身份")
    private RoleEnum role;

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

    @Schema(name = "用户状态")
    private UserStatusEnum status;

    @Schema(name = "用户数据")
    private UserData userData;

    @Schema(name = "cf的username")
    private String cfUsername;

    @Schema(name = "github地址")
    private String github;

    @Schema(name = "博客地址")
    private String blog;

    public User(String email, String nickname, String password, RoleEnum role, UserContext userContext) {
        super(newUserId(), userContext);
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }

    public static String newUserId() {
        return USER_ID_PREFIX + newSnowflakeId();
    }

}
