package com.g01502.jiaoxuebackend.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g01502.jiaoxuebackend.common.exception.BusinessException;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.CourseCreateRequest;
import com.g01502.jiaoxuebackend.dto.CourseUpdateRequest;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.CourseEntity;
import com.g01502.jiaoxuebackend.mapper.CourseMapper;
import com.g01502.jiaoxuebackend.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 课程业务实现。
 */
@Slf4j
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, CourseEntity> implements CourseService {

    @Override
    public Long createCourse(CourseCreateRequest request) {
        CourseEntity entity = new CourseEntity();
        BeanUtils.copyProperties(request, entity);
        entity.setStatus("draft");
        if (entity.getPrice() == null) {
            entity.setPrice(BigDecimal.ZERO);
        }
        save(entity);
        log.info("创建课程成功, courseId={}, teacherId={}", entity.getId(), entity.getTeacherId());
        return entity.getId();
    }

    @Override
    public void updateCourse(CourseUpdateRequest request) {
        CourseEntity entity = getById(request.getId());
        if (entity == null) {
            throw new BusinessException(404, "课程不存在");
        }
        BeanUtils.copyProperties(request, entity);
        updateById(entity);
        log.info("更新课程成功, courseId={}", entity.getId());
    }

    @Override
    public void publishCourse(Long courseId) {
        CourseEntity entity = getById(courseId);
        if (entity == null) {
            throw new BusinessException(404, "课程不存在");
        }
        entity.setStatus("published");
        updateById(entity);
        log.info("发布课程成功, courseId={}", courseId);
    }

    @Override
    public PageResponse<CourseEntity> pageCourses(PageRequest request) {
        Page<CourseEntity> page = new Page<>(request.getCurrent(), request.getSize());
        Page<CourseEntity> result = lambdaQuery()
                .like(request.getKeyword() != null && !request.getKeyword().isBlank(), CourseEntity::getTitle, request.getKeyword())
                .page(page);

        PageResponse<CourseEntity> response = new PageResponse<>();
        response.setCurrent(result.getCurrent());
        response.setSize(result.getSize());
        response.setTotal(result.getTotal());
        response.setRecords(result.getRecords());
        return response;
    }
}
