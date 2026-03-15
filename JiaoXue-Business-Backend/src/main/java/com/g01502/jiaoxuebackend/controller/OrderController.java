package com.g01502.jiaoxuebackend.controller;

import com.g01502.jiaoxuebackend.common.model.ApiResponse;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.OrderCreateRequest;
import com.g01502.jiaoxuebackend.dto.OrderPayRequest;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.OrderRecordEntity;
import com.g01502.jiaoxuebackend.service.OrderRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 订单管理接口。
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRecordService orderRecordService;

    @PostMapping
    public ApiResponse<Long> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        return ApiResponse.success("创建订单成功", orderRecordService.createOrder(request));
    }

    @PostMapping("/pay")
    public ApiResponse<Void> payOrder(@Valid @RequestBody OrderPayRequest request) {
        orderRecordService.payOrder(request);
        return ApiResponse.success("支付成功", null);
    }

    @GetMapping("/page")
    public ApiResponse<PageResponse<OrderRecordEntity>> pageOrders(@Valid PageRequest request) {
        return ApiResponse.success(orderRecordService.pageOrders(request));
    }
}
