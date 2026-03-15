package com.g01502.jiaoxuebackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 选课报名记录，关联学生与课程的学习状态。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("edu_course_enrollment")
public class CourseEnrollmentEntity extends BaseEntity {

    private Long courseId;
    private Long studentId;
    private Integer progress;
    private String status;
    private LocalDateTime lastStudyTime;
}
