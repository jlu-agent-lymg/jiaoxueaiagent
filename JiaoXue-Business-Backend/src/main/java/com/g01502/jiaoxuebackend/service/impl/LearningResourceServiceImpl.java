package com.g01502.jiaoxuebackend.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g01502.jiaoxuebackend.common.exception.BusinessException;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.LearningResourceEntity;
import com.g01502.jiaoxuebackend.mapper.LearningResourceMapper;
import com.g01502.jiaoxuebackend.service.LearningResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 学习资源业务实现。
 */
@Slf4j
@Service
public class LearningResourceServiceImpl extends ServiceImpl<LearningResourceMapper, LearningResourceEntity> implements LearningResourceService {

    @Override
    public Long createResource(LearningResourceEntity resource) {
        resource.setAuditStatus(resource.getAuditStatus() == null ? "pending" : resource.getAuditStatus());
        save(resource);
        log.info("创建学习资源成功, resourceId={}, uploaderId={}", resource.getId(), resource.getUploaderId());
        return resource.getId();
    }

    @Override
    public void auditResource(Long id, String auditStatus) {
        LearningResourceEntity entity = getById(id);
        if (entity == null) {
            throw new BusinessException(404, "资源不存在");
        }
        entity.setAuditStatus(auditStatus);
        updateById(entity);
        log.info("审核学习资源完成, resourceId={}, auditStatus={}", id, auditStatus);
    }

    @Override
    public PageResponse<LearningResourceEntity> pageResources(PageRequest request) {
        Page<LearningResourceEntity> page = new Page<>(request.getCurrent(), request.getSize());
        Page<LearningResourceEntity> result = lambdaQuery()
                .like(request.getKeyword() != null && !request.getKeyword().isBlank(), LearningResourceEntity::getTitle, request.getKeyword())
                .page(page);

        PageResponse<LearningResourceEntity> response = new PageResponse<>();
        response.setCurrent(result.getCurrent());
        response.setSize(result.getSize());
        response.setTotal(result.getTotal());
        response.setRecords(result.getRecords());
        return response;
    }
}
