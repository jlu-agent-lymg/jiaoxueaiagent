package com.g01502.jiaoxuebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.ExamSubmitRequest;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.ExamRecordEntity;

/**
 * 考试记录业务接口。
 */
public interface ExamRecordService extends IService<ExamRecordEntity> {

    Long submitExam(ExamSubmitRequest request);

    PageResponse<ExamRecordEntity> pageRecords(PageRequest request);
}
