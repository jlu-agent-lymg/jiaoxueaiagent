package com.g01502.jiaoxuebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.ExamPaperEntity;

/**
 * 试卷业务接口。
 */
public interface ExamPaperService extends IService<ExamPaperEntity> {

    Long createPaper(ExamPaperEntity paper);

    void publishPaper(Long paperId);

    PageResponse<ExamPaperEntity> pagePapers(PageRequest request);
}
