package com.g01502.jiaoxuebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.OrderCreateRequest;
import com.g01502.jiaoxuebackend.dto.OrderPayRequest;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.OrderRecordEntity;

/**
 * 订单业务接口。
 */
public interface OrderRecordService extends IService<OrderRecordEntity> {

    Long createOrder(OrderCreateRequest request);

    void payOrder(OrderPayRequest request);

    PageResponse<OrderRecordEntity> pageOrders(PageRequest request);
}
