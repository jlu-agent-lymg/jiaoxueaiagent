package com.g01502.jiaoxuebackend.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g01502.jiaoxuebackend.common.exception.BusinessException;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.LearningPlanCreateRequest;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.LearningPlanEntity;
import com.g01502.jiaoxuebackend.mapper.LearningPlanMapper;
import com.g01502.jiaoxuebackend.service.LearningPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 学习计划业务实现。
 */
@Slf4j
@Service
public class LearningPlanServiceImpl extends ServiceImpl<LearningPlanMapper, LearningPlanEntity> implements LearningPlanService {

    @Override
    public Long createPlan(LearningPlanCreateRequest request) {
        LearningPlanEntity entity = new LearningPlanEntity();
        entity.setStudentId(request.getStudentId());
        entity.setCourseId(request.getCourseId());
        entity.setDailyTargetMinutes(request.getDailyTargetMinutes());
        entity.setTargetFinishDate(request.getTargetFinishDate());
        entity.setRemarks(request.getRemarks());
        entity.setStatus("running");
        save(entity);
        log.info("创建学习计划成功, planId={}, studentId={}, courseId={}", entity.getId(), entity.getStudentId(), entity.getCourseId());
        return entity.getId();
    }

    @Override
    public void updateStatus(Long planId, String status) {
        LearningPlanEntity entity = getById(planId);
        if (entity == null) {
            throw new BusinessException(404, "学习计划不存在");
        }
        entity.setStatus(status);
        updateById(entity);
        log.info("更新学习计划状态, planId={}, status={}", planId, status);
    }

    @Override
    public PageResponse<LearningPlanEntity> pagePlans(PageRequest request) {
        Page<LearningPlanEntity> page = new Page<>(request.getCurrent(), request.getSize());
        Page<LearningPlanEntity> result = page(page);

        PageResponse<LearningPlanEntity> response = new PageResponse<>();
        response.setCurrent(result.getCurrent());
        response.setSize(result.getSize());
        response.setTotal(result.getTotal());
        response.setRecords(result.getRecords());
        return response;
    }
}
