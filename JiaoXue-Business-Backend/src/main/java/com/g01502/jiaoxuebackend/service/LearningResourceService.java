package com.g01502.jiaoxuebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.LearningResourceEntity;

/**
 * 学习资源业务接口。
 */
public interface LearningResourceService extends IService<LearningResourceEntity> {

    Long createResource(LearningResourceEntity resource);

    void auditResource(Long id, String auditStatus);

    PageResponse<LearningResourceEntity> pageResources(PageRequest request);
}
