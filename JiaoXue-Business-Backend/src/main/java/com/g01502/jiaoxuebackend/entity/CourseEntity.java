package com.g01502.jiaoxuebackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 课程主表，描述课程基础信息和发布状态。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("edu_course")
public class CourseEntity extends BaseEntity {

    private String title;
    private String summary;
    private Long teacherId;
    private String category;
    private String difficulty;
    private BigDecimal price;
    private String status;
}
