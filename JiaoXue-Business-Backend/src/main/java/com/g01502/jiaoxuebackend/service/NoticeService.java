package com.g01502.jiaoxuebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.NoticePublishRequest;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.NoticeEntity;

/**
 * 公告业务接口。
 */
public interface NoticeService extends IService<NoticeEntity> {

    Long publishNotice(NoticePublishRequest request);

    PageResponse<NoticeEntity> pageNotices(PageRequest request);
}
