package com.g01502.jiaoxuebackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 考试提交请求。
 */
@Data
public class ExamSubmitRequest {

    @NotNull(message = "试卷 ID 不能为空")
    private Long examPaperId;

    @NotNull(message = "学生 ID 不能为空")
    private Long studentId;

    @NotBlank(message = "答题内容不能为空")
    private String answerJson;

    @NotNull(message = "得分不能为空")
    private Integer score;
}
