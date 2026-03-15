package com.g01502.jiaoxuebackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 学习计划，约束学生每日学习目标和截止日期。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("edu_learning_plan")
public class LearningPlanEntity extends BaseEntity {

    private Long studentId;
    private Long courseId;
    private Integer dailyTargetMinutes;
    private LocalDate targetFinishDate;
    private String status;
    private String remarks;
}
