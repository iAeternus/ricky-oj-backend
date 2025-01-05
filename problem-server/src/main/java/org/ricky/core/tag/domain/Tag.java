package org.ricky.core.tag.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.ricky.common.context.UserContext;
import org.ricky.common.domain.AggregateRoot;
import org.ricky.common.domain.marker.Identified;
import org.ricky.common.validation.color.Color;
import org.ricky.common.validation.id.Id;
import org.ricky.core.tag.domain.event.TagDeletedEvent;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import static org.ricky.common.constants.CommonConstants.*;
import static org.ricky.common.utils.SnowflakeIdGenerator.newSnowflakeId;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/27
 * @className Tag
 * @desc 题目标签
 */
@Getter
@Document(TAG_COLLECTION)
@TypeAlias(TAG_COLLECTION)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Tag extends AggregateRoot {

    /**
     * 名称
     */
    @NotBlank
    private String name;

    /**
     * 颜色
     */
    @Color
    private String color;

    /**
     * 所属OJ
     */
    private String oj;

    /**
     * 团队ID
     */
    @Id(prefix = GROUP_ID_PREFIX)
    private String groupId;

    public Tag(String name, String color, String oj, String groupId, UserContext userContext) {
        super(newTagId(), userContext);
        this.name = name;
        this.color = color;
        this.oj = oj;
        this.groupId = groupId;
        addOpsLog("新建标签", userContext);
    }

    public static String newTagId() {
        return TAG_ID_PREFIX + newSnowflakeId();
    }

    public void onDelete(UserContext userContext) {
        raiseEvent(new TagDeletedEvent(getId(), userContext));
    }

    public void update(String name, String color, String oj, String groupId, UserContext userContext) {
        this.name = name;
        this.color = color;
        this.oj = oj;
        this.groupId = groupId;
        addOpsLog("修改标签信息", userContext);
    }

}
