package org.ricky.core.problem.domain.setting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.ricky.common.domain.marker.ValueObject;
import org.ricky.common.exception.MyException;
import org.ricky.common.validation.id.Id;

import static org.ricky.common.constants.CommonConstants.GROUP_ID_PREFIX;
import static org.ricky.common.exception.ErrorCodeEnum.NOT_GROUPED_PROBLEM;
import static org.ricky.common.exception.ErrorCodeEnum.REQUIRE_GROUP_ID;
import static org.ricky.common.utils.CollectionUtils.mapOf;
import static org.ricky.common.utils.ValidationUtils.isBlank;
import static org.ricky.common.utils.ValidationUtils.isNotBlank;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/27
 * @className GroupSetting
 * @desc 团队设置
 */
@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupSetting implements ValueObject {

    /**
     * 是否为团队内的题目
     */
    boolean isGroup;

    /**
     * 团队ID
     */
    @Id(prefix = GROUP_ID_PREFIX)
    String teamId;

    public static GroupSetting defaultGroupSetting() {
        return GroupSetting.builder()
                .isGroup(false)
                .teamId(null)
                .build();
    }

    public void setGroup(String groupId) {
        this.isGroup = true;
        this.teamId = groupId;
    }

    @Override
    public void validate() {
        if (isGroup && isBlank(teamId)) {
            throw new MyException(REQUIRE_GROUP_ID, "Group ID is required.");
        }
        if (!isGroup && isNotBlank(teamId)) {
            throw new MyException(NOT_GROUPED_PROBLEM, "It's not a team problem.",
                    mapOf("wrongTeamId", teamId));
        }
    }

    @Override
    public void correct() {
        if (!isGroup) {
            teamId = null;
        } else if (isBlank(teamId)) {
            teamId = "default";
        }
    }
}
