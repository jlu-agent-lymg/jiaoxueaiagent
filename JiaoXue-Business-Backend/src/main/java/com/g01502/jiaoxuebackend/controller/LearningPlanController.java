package com.g01502.jiaoxuebackend.controller;

import com.g01502.jiaoxuebackend.common.model.ApiResponse;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.LearningPlanCreateRequest;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.LearningPlanEntity;
import com.g01502.jiaoxuebackend.service.LearningPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 学习计划管理接口。
 */
@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class LearningPlanController {

    private final LearningPlanService learningPlanService;

    @PostMapping
    public ApiResponse<Long> createPlan(@Valid @RequestBody LearningPlanCreateRequest request) {
        return ApiResponse.success("创建学习计划成功", learningPlanService.createPlan(request));
    }

    @PostMapping("/{id}/status")
    public ApiResponse<Void> updateStatus(@PathVariable("id") Long id, @RequestParam("status") String status) {
        learningPlanService.updateStatus(id, status);
        return ApiResponse.success("更新学习计划状态成功", null);
    }

    @GetMapping("/page")
    public ApiResponse<PageResponse<LearningPlanEntity>> pagePlans(@Valid PageRequest request) {
        return ApiResponse.success(learningPlanService.pagePlans(request));
    }
}
