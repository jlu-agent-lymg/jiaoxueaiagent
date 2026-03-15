package com.g01502.jiaoxuebackend.controller;

import com.g01502.jiaoxuebackend.common.model.ApiResponse;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.EnrollmentCreateRequest;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.CourseEnrollmentEntity;
import com.g01502.jiaoxuebackend.service.CourseEnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 报名管理接口。
 */
@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class CourseEnrollmentController {

    private final CourseEnrollmentService enrollmentService;

    @PostMapping
    public ApiResponse<Long> enrollCourse(@Valid @RequestBody EnrollmentCreateRequest request) {
        return ApiResponse.success("报名成功", enrollmentService.enrollCourse(request));
    }

    @GetMapping("/page")
    public ApiResponse<PageResponse<CourseEnrollmentEntity>> pageEnrollments(@Valid PageRequest request) {
        return ApiResponse.success(enrollmentService.pageEnrollments(request));
    }
}
