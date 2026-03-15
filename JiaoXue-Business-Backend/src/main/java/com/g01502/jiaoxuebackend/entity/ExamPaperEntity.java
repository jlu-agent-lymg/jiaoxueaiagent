package com.g01502.jiaoxuebackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 试卷信息，题目内容可通过 JSON 结构存储。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("edu_exam_paper")
public class ExamPaperEntity extends BaseEntity {

    private Long courseId;
    private String title;
    private Integer totalScore;
    private Integer durationMinutes;
    private Integer passingScore;
    private String questionJson;
    private String status;
}
