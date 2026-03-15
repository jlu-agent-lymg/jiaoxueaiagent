package com.g01502.jiaoxuebackend.controller;

import com.g01502.jiaoxuebackend.common.model.ApiResponse;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.LearningResourceEntity;
import com.g01502.jiaoxuebackend.service.LearningResourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 学习资源管理接口。
 */
@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
public class LearningResourceController {

    private final LearningResourceService resourceService;

    @PostMapping
    public ApiResponse<Long> createResource(@Valid @RequestBody LearningResourceEntity resource) {
        return ApiResponse.success("创建资源成功", resourceService.createResource(resource));
    }

    @PostMapping("/{id}/audit")
    public ApiResponse<Void> auditResource(@PathVariable("id") Long id, @RequestParam("status") String status) {
        resourceService.auditResource(id, status);
        return ApiResponse.success("审核资源成功", null);
    }

    @GetMapping("/page")
    public ApiResponse<PageResponse<LearningResourceEntity>> pageResources(@Valid PageRequest request) {
        return ApiResponse.success(resourceService.pageResources(request));
    }
}
