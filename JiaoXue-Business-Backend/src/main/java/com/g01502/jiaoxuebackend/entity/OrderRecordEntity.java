package com.g01502.jiaoxuebackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单记录，支持课程付费和会员开通等交易场景。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("edu_order")
public class OrderRecordEntity extends BaseEntity {

    private String orderNo;
    private Long userId;
    private String bizType;
    private Long bizId;
    private BigDecimal amount;
    private String payStatus;
    private String payChannel;
    private LocalDateTime paidTime;
}
