package com.g01502.jiaoxuebackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单创建请求。
 */
@Data
public class OrderCreateRequest {

    @NotNull(message = "用户 ID 不能为空")
    private Long userId;

    @NotNull(message = "业务 ID 不能为空")
    private Long bizId;

    private String bizType;

    @NotNull(message = "订单金额不能为空")
    private BigDecimal amount;
}
