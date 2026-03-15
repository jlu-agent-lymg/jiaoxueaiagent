package com.g01502.jiaoxuebackend.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g01502.jiaoxuebackend.common.exception.BusinessException;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.EnrollmentCreateRequest;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.CourseEnrollmentEntity;
import com.g01502.jiaoxuebackend.mapper.CourseEnrollmentMapper;
import com.g01502.jiaoxuebackend.service.CourseEnrollmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 选课报名业务实现。
 */
@Slf4j
@Service
public class CourseEnrollmentServiceImpl extends ServiceImpl<CourseEnrollmentMapper, CourseEnrollmentEntity> implements CourseEnrollmentService {

    @Override
    public Long enrollCourse(EnrollmentCreateRequest request) {
        long exists = lambdaQuery()
                .eq(CourseEnrollmentEntity::getCourseId, request.getCourseId())
                .eq(CourseEnrollmentEntity::getStudentId, request.getStudentId())
                .count();
        if (exists > 0) {
            throw new BusinessException(409, "该学生已报名当前课程");
        }
        CourseEnrollmentEntity entity = new CourseEnrollmentEntity();
        entity.setCourseId(request.getCourseId());
        entity.setStudentId(request.getStudentId());
        entity.setProgress(0);
        entity.setStatus("studying");
        entity.setLastStudyTime(LocalDateTime.now());
        save(entity);
        log.info("报名课程成功, enrollmentId={}, courseId={}, studentId={}", entity.getId(), entity.getCourseId(), entity.getStudentId());
        return entity.getId();
    }

    @Override
    public PageResponse<CourseEnrollmentEntity> pageEnrollments(PageRequest request) {
        Page<CourseEnrollmentEntity> page = new Page<>(request.getCurrent(), request.getSize());
        Page<CourseEnrollmentEntity> result = page(page);

        PageResponse<CourseEnrollmentEntity> response = new PageResponse<>();
        response.setCurrent(result.getCurrent());
        response.setSize(result.getSize());
        response.setTotal(result.getTotal());
        response.setRecords(result.getRecords());
        return response;
    }
}
