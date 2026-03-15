package com.g01502.jiaoxuebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.LearningPlanCreateRequest;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.LearningPlanEntity;

/**
 * 学习计划业务接口。
 */
public interface LearningPlanService extends IService<LearningPlanEntity> {

    Long createPlan(LearningPlanCreateRequest request);

    void updateStatus(Long planId, String status);

    PageResponse<LearningPlanEntity> pagePlans(PageRequest request);
}
