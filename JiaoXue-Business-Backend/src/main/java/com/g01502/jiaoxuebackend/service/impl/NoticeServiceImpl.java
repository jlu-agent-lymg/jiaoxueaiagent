package com.g01502.jiaoxuebackend.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.NoticePublishRequest;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.NoticeEntity;
import com.g01502.jiaoxuebackend.mapper.NoticeMapper;
import com.g01502.jiaoxuebackend.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 公告业务实现。
 */
@Slf4j
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, NoticeEntity> implements NoticeService {

    @Override
    public Long publishNotice(NoticePublishRequest request) {
        NoticeEntity entity = new NoticeEntity();
        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        entity.setPublisherId(request.getPublisherId());
        entity.setScopeType(request.getScopeType() == null ? "all" : request.getScopeType());
        entity.setStatus("published");
        entity.setPublishTime(LocalDateTime.now());
        save(entity);
        log.info("发布公告成功, noticeId={}, publisherId={}", entity.getId(), entity.getPublisherId());
        return entity.getId();
    }

    @Override
    public PageResponse<NoticeEntity> pageNotices(PageRequest request) {
        Page<NoticeEntity> page = new Page<>(request.getCurrent(), request.getSize());
        Page<NoticeEntity> result = lambdaQuery()
                .like(request.getKeyword() != null && !request.getKeyword().isBlank(), NoticeEntity::getTitle, request.getKeyword())
                .page(page);

        PageResponse<NoticeEntity> response = new PageResponse<>();
        response.setCurrent(result.getCurrent());
        response.setSize(result.getSize());
        response.setTotal(result.getTotal());
        response.setRecords(result.getRecords());
        return response;
    }
}
