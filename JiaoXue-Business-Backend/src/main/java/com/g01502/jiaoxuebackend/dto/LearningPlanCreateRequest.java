package com.g01502.jiaoxuebackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * 学习计划创建请求。
 */
@Data
public class LearningPlanCreateRequest {

    @NotNull(message = "学生 ID 不能为空")
    private Long studentId;

    @NotNull(message = "课程 ID 不能为空")
    private Long courseId;

    @NotNull(message = "每日学习时长不能为空")
    private Integer dailyTargetMinutes;

    @NotNull(message = "目标完成日期不能为空")
    private LocalDate targetFinishDate;

    private String remarks;
}
