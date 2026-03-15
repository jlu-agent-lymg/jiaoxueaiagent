package com.g01502.jiaoxuebackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 公告通知，用于课程变更、活动提醒和系统通知。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("edu_notice")
public class NoticeEntity extends BaseEntity {

    private String title;
    private String content;
    private Long publisherId;
    private String scopeType;
    private String status;
    private LocalDateTime publishTime;
}
