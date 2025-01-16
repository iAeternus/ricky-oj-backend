package org.ricky.dto.alter.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.ricky.common.domain.UploadedFile;
import org.ricky.common.domain.marker.Identified;
import org.ricky.common.domain.marker.Response;
import org.ricky.common.domain.program.Program;
import org.ricky.common.exception.MyException;
import org.ricky.common.utils.ValidationUtils;
import org.ricky.common.validation.id.Id;
import org.ricky.dto.alter.JudgeStatusEnum;

import java.time.Instant;
import java.util.List;

import static org.ricky.common.constants.CommonConstants.CASE_ID_PREFIX;
import static org.ricky.common.constants.CommonConstants.JUDGE_CASE_ID_PREFIX;
import static org.ricky.common.exception.ErrorCodeEnum.INVALID_SUBMIT_TYPE;
import static org.ricky.common.utils.CollectionUtils.mapOf;
import static org.ricky.common.utils.ValidationUtils.requireNonNull;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/9
 * @className Judge
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Judge implements Response {

    @Schema(name = "题目ID")
    String problemId;

    @Schema(name = "题目自定义ID，例如：ROJ-1")
    String customId;

    @Schema(name = "用户昵称")
    String nickname;

    @Schema(name = "true=全部人可见 false=仅自己可见")
    Boolean share;

    @Schema(name = "提交类型")
    SubmitTypeEnum type;

    @Schema(name = "是否为远程评测")
    Boolean isRemote;

    @Schema(name = "程序")
    Program program;

    @Schema(name = "评测样例集合")
    List<JudgeCase> judgeCases;

    @Schema(name = "提交者IP")
    String ip;

    @Schema(name = "提交时间")
    Instant submitAt;

    @Schema(name = "评测状态")
    JudgeStatusEnum status;

    @Schema(name = "错误提醒（编译错误，或者vj提醒）")
    String errorMessage;

    @Schema(name = "运行时间(ms)")
    Integer time;

    @Schema(name = "运行内存(kb)")
    Integer memory;

    @Schema(name = "修改时间")
    Instant updatedAt;

    public String getLangName() {
        requireNonNull(program, "Program must be not null.");

        return program.getLanguage().getName();
    }

    @Getter
    public enum SubmitTypeEnum {

        CONTEXT((short) 0),
        GENERAL((short) 1),
        TEST((short) 2),
        ;

        final short key;

        SubmitTypeEnum(short key) {
            this.key = key;
        }

        public static SubmitTypeEnum of(short key) {
            for (SubmitTypeEnum type : values()) {
                if (type.getKey() == key) {
                    return type;
                }
            }
            throw new MyException(INVALID_SUBMIT_TYPE, "Invalid submit type", mapOf("key", key));
        }

    }


    @Getter
    @Builder
    @EqualsAndHashCode
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class JudgeCase implements Identified {

        /**
         * ID
         */
        @NotBlank
        @Id(prefix = JUDGE_CASE_ID_PREFIX)
        private String id;

        /**
         * 测试用例ID
         */
        @NotBlank
        @Id(prefix = CASE_ID_PREFIX)
        private String caseId;

        /**
         * 该测试样例所用时间(ms)
         */
        private Integer time;

        /**
         * 该测试样例所用空间(KB)
         */
        private Integer memory;

        /**
         * 该测试样例状态
         */
        @NotNull
        private JudgeStatusEnum status;

        /**
         * 样例输入文件
         */
        @Valid
        @NotNull
        private UploadedFile input;

        /**
         * 样例输出文件
         */
        @Valid
        @NotNull
        private UploadedFile output;

        /**
         * 用户样例输出文件
         */
        @Valid
        private UploadedFile userOutput;

        /**
         * 排序
         */
        @NotNull
        private Integer seq;

        @Override
        public String getIdentifier() {
            return id;
        }
    }

}
