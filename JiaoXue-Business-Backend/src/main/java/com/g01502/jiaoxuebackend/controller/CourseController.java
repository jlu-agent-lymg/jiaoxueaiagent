package com.g01502.jiaoxuebackend.controller;

import com.g01502.jiaoxuebackend.common.model.ApiResponse;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.CourseCreateRequest;
import com.g01502.jiaoxuebackend.dto.CourseUpdateRequest;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.CourseEntity;
import com.g01502.jiaoxuebackend.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 课程管理接口。
 */
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ApiResponse<Long> createCourse(@Valid @RequestBody CourseCreateRequest request) {
        return ApiResponse.success("创建课程成功", courseService.createCourse(request));
    }

    @PutMapping
    public ApiResponse<Void> updateCourse(@Valid @RequestBody CourseUpdateRequest request) {
        courseService.updateCourse(request);
        return ApiResponse.success("更新课程成功", null);
    }

    @PostMapping("/{id}/publish")
    public ApiResponse<Void> publishCourse(@PathVariable("id") Long id) {
        courseService.publishCourse(id);
        return ApiResponse.success("发布课程成功", null);
    }

    @GetMapping("/page")
    public ApiResponse<PageResponse<CourseEntity>> pageCourses(@Valid PageRequest request) {
        return ApiResponse.success(courseService.pageCourses(request));
    }
}
