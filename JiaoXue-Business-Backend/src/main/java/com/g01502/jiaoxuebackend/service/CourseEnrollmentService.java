package com.g01502.jiaoxuebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.EnrollmentCreateRequest;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.CourseEnrollmentEntity;

/**
 * 选课报名业务接口。
 */
public interface CourseEnrollmentService extends IService<CourseEnrollmentEntity> {

    Long enrollCourse(EnrollmentCreateRequest request);

    PageResponse<CourseEnrollmentEntity> pageEnrollments(PageRequest request);
}
