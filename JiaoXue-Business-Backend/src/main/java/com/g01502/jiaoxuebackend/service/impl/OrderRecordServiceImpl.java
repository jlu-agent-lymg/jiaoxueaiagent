package com.g01502.jiaoxuebackend.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g01502.jiaoxuebackend.common.exception.BusinessException;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.OrderCreateRequest;
import com.g01502.jiaoxuebackend.dto.OrderPayRequest;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.OrderRecordEntity;
import com.g01502.jiaoxuebackend.mapper.OrderRecordMapper;
import com.g01502.jiaoxuebackend.service.OrderRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 订单业务实现。
 */
@Slf4j
@Service
public class OrderRecordServiceImpl extends ServiceImpl<OrderRecordMapper, OrderRecordEntity> implements OrderRecordService {

    @Override
    public Long createOrder(OrderCreateRequest request) {
        OrderRecordEntity entity = new OrderRecordEntity();
        entity.setOrderNo(generateOrderNo());
        entity.setUserId(request.getUserId());
        entity.setBizId(request.getBizId());
        entity.setBizType(request.getBizType() == null ? "course" : request.getBizType());
        entity.setAmount(request.getAmount());
        entity.setPayStatus("unpaid");
        save(entity);
        log.info("创建订单成功, orderId={}, orderNo={}, userId={}", entity.getId(), entity.getOrderNo(), entity.getUserId());
        return entity.getId();
    }

    @Override
    public void payOrder(OrderPayRequest request) {
        OrderRecordEntity entity = getById(request.getOrderId());
        if (entity == null) {
            throw new BusinessException(404, "订单不存在");
        }
        if ("paid".equals(entity.getPayStatus())) {
            throw new BusinessException(409, "订单已支付，请勿重复支付");
        }
        entity.setPayStatus("paid");
        entity.setPayChannel(request.getPayChannel());
        entity.setPaidTime(LocalDateTime.now());
        updateById(entity);
        log.info("订单支付成功, orderId={}, orderNo={}, payChannel={}", entity.getId(), entity.getOrderNo(), entity.getPayChannel());
    }

    @Override
    public PageResponse<OrderRecordEntity> pageOrders(PageRequest request) {
        Page<OrderRecordEntity> page = new Page<>(request.getCurrent(), request.getSize());
        Page<OrderRecordEntity> result = lambdaQuery()
                .like(request.getKeyword() != null && !request.getKeyword().isBlank(), OrderRecordEntity::getOrderNo, request.getKeyword())
                .page(page);

        PageResponse<OrderRecordEntity> response = new PageResponse<>();
        response.setCurrent(result.getCurrent());
        response.setSize(result.getSize());
        response.setTotal(result.getTotal());
        response.setRecords(result.getRecords());
        return response;
    }

    /**
     * 生成业务可追踪的订单号：日期时间 + 四位随机数。
     */
    private String generateOrderNo() {
        String timePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int randomPart = ThreadLocalRandom.current().nextInt(1000, 9999);
        return "ORD" + timePart + randomPart;
    }
}
