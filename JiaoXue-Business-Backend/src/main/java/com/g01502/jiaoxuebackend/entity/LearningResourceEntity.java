package com.g01502.jiaoxuebackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 学习资源信息，支持文档、题库、课件等素材。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("edu_resource")
public class LearningResourceEntity extends BaseEntity {

    private Long courseId;
    private String title;
    private String resourceType;
    private String url;
    private Long uploaderId;
    private String auditStatus;
}
