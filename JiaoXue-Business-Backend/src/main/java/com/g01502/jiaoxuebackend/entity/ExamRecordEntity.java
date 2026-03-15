package com.g01502.jiaoxuebackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 考试提交记录，用于回溯答题与评分结果。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("edu_exam_record")
public class ExamRecordEntity extends BaseEntity {

    private Long examPaperId;
    private Long studentId;
    private Integer score;
    private LocalDateTime submitTime;
    private String answerJson;
    private String result;
}
