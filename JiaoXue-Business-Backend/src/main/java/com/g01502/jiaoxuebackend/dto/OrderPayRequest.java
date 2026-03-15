package com.g01502.jiaoxuebackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 订单支付请求。
 */
@Data
public class OrderPayRequest {

    @NotNull(message = "订单 ID 不能为空")
    private Long orderId;

    @NotBlank(message = "支付渠道不能为空")
    private String payChannel;
}
