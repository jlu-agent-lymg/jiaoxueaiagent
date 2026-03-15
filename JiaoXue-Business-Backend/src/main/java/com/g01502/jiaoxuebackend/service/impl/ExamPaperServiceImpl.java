package com.g01502.jiaoxuebackend.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g01502.jiaoxuebackend.common.exception.BusinessException;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.ExamPaperEntity;
import com.g01502.jiaoxuebackend.mapper.ExamPaperMapper;
import com.g01502.jiaoxuebackend.service.ExamPaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 试卷业务实现。
 */
@Slf4j
@Service
public class ExamPaperServiceImpl extends ServiceImpl<ExamPaperMapper, ExamPaperEntity> implements ExamPaperService {

    @Override
    public Long createPaper(ExamPaperEntity paper) {
        paper.setStatus(paper.getStatus() == null ? "draft" : paper.getStatus());
        save(paper);
        log.info("创建试卷成功, paperId={}, courseId={}", paper.getId(), paper.getCourseId());
        return paper.getId();
    }

    @Override
    public void publishPaper(Long paperId) {
        ExamPaperEntity entity = getById(paperId);
        if (entity == null) {
            throw new BusinessException(404, "试卷不存在");
        }
        entity.setStatus("published");
        updateById(entity);
        log.info("发布试卷成功, paperId={}", paperId);
    }

    @Override
    public PageResponse<ExamPaperEntity> pagePapers(PageRequest request) {
        Page<ExamPaperEntity> page = new Page<>(request.getCurrent(), request.getSize());
        Page<ExamPaperEntity> result = lambdaQuery()
                .like(request.getKeyword() != null && !request.getKeyword().isBlank(), ExamPaperEntity::getTitle, request.getKeyword())
                .page(page);

        PageResponse<ExamPaperEntity> response = new PageResponse<>();
        response.setCurrent(result.getCurrent());
        response.setSize(result.getSize());
        response.setTotal(result.getTotal());
        response.setRecords(result.getRecords());
        return response;
    }
}
