package com.g01502.jiaoxuebackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课程课节，承载视频、文档、直播回放等学习内容。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("edu_course_lesson")
public class CourseLessonEntity extends BaseEntity {

    private Long courseId;
    private String title;
    private String contentType;
    private String contentUrl;
    private Integer durationMinutes;
    private Integer sortNo;
    private String status;
}
