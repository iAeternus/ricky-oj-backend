package org.ricky.core.submit.domain.judgecase;

import lombok.*;
import org.ricky.common.domain.UploadedFile;
import org.ricky.common.domain.marker.Identified;
import org.ricky.core.submit.domain.JudgeStatusEnum;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/6
 * @className JudgeCase
 * @desc 评测样例
 */
@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JudgeCase implements Identified {

    /**
     * ID
     */
    private String id;

    /**
     * 测试用例ID
     */
    private String caseId;

    /**
     * 该测试样例所用时间(ms)
     */
    private int time;

    /**
     * 该测试样例所用空间(KB)
     */
    private int memory;

    /**
     * 该测试样例状态
     */
    private JudgeStatusEnum status;

    /**
     * 样例输入文件 TODO
     */
    private UploadedFile input;

    /**
     * 样例输出文件
     */
    private UploadedFile output;

    /**
     * 用户样例输出文件
     */
    private UploadedFile userOutput;

    /**
     * 排序
     */
    private int seq;

    @Override
    public String getIdentifier() {
        return null;
    }
}
