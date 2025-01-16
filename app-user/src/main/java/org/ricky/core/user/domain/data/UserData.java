package org.ricky.core.user.domain.data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ricky.common.validation.collection.NoNullElement;
import org.ricky.core.user.domain.data.accepted.AcceptedProblem;

import java.util.List;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/27
 * @className UserData
 * @desc 用户数据
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserData {

    /**
     * cf得分
     */
    Integer rating;

    /**
     * AC的题目集合
     */
    @Valid
    @NotNull
    @NoNullElement
    List<AcceptedProblem> acceptedProblems;

    // TODO 后期迭代：统计用户每天刷了多少道题

    public static UserData defaultStudentData() {
        UserData userData = new UserData();
        userData.rating = 0; // 未获取默认为0
        userData.acceptedProblems = List.of();
        return userData;
    }

    public void setCfInfo(int rating) {
        this.rating = rating;
    }

    public void addAccepted(String problemId, String submitId) {
        acceptedProblems.add(new AcceptedProblem(problemId, submitId));
    }

}
