package com.g01502.jiaoxuebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.CourseCreateRequest;
import com.g01502.jiaoxuebackend.dto.CourseUpdateRequest;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.CourseEntity;

/**
 * 课程业务接口。
 */
public interface CourseService extends IService<CourseEntity> {

    Long createCourse(CourseCreateRequest request);

    void updateCourse(CourseUpdateRequest request);

    void publishCourse(Long courseId);

    PageResponse<CourseEntity> pageCourses(PageRequest request);
}
