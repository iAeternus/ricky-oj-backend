package org.ricky.core.user.fetch.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Response;
import org.ricky.core.user.domain.GenderEnum;
import org.ricky.core.user.domain.data.UserData;
import org.ricky.core.user.domain.title.Title;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/4
 * @className UserInfoResponse
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfoResponse implements Response {

    @Schema(name = "用户ID")
    String userId;

    @Schema(name = "昵称")
    String nickname;

    @Schema(name = "邮箱")
    String email;

    @Schema(name = "手机号")
    String mobile;

    @Schema(name = "用户数据")
    UserData userData;

    @Schema(name = "学校")
    String school;

    @Schema(name = "专业")
    String course;

    @Schema(name = "学号")
    String idNumber;

    @Schema(name = "性别")
    GenderEnum gender;

    @Schema(name = "真实姓名")
    String realName;

    @Schema(name = "个性介绍")
    String signature;

    @Schema(name = "头衔、称号")
    Title title;

}
