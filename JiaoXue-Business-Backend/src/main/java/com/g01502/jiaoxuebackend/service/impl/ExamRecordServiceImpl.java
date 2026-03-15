package com.g01502.jiaoxuebackend.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.ExamSubmitRequest;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.ExamRecordEntity;
import com.g01502.jiaoxuebackend.mapper.ExamRecordMapper;
import com.g01502.jiaoxuebackend.service.ExamRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 考试记录业务实现。
 */
@Slf4j
@Service
public class ExamRecordServiceImpl extends ServiceImpl<ExamRecordMapper, ExamRecordEntity> implements ExamRecordService {

    @Override
    public Long submitExam(ExamSubmitRequest request) {
        ExamRecordEntity entity = new ExamRecordEntity();
        entity.setExamPaperId(request.getExamPaperId());
        entity.setStudentId(request.getStudentId());
        entity.setAnswerJson(request.getAnswerJson());
        entity.setScore(request.getScore());
        entity.setSubmitTime(LocalDateTime.now());
        entity.setResult(request.getScore() >= 60 ? "pass" : "fail");
        save(entity);
        log.info("提交考试成功, recordId={}, paperId={}, studentId={}, score={}", entity.getId(), entity.getExamPaperId(), entity.getStudentId(), entity.getScore());
        return entity.getId();
    }

    @Override
    public PageResponse<ExamRecordEntity> pageRecords(PageRequest request) {
        Page<ExamRecordEntity> page = new Page<>(request.getCurrent(), request.getSize());
        Page<ExamRecordEntity> result = page(page);

        PageResponse<ExamRecordEntity> response = new PageResponse<>();
        response.setCurrent(result.getCurrent());
        response.setSize(result.getSize());
        response.setTotal(result.getTotal());
        response.setRecords(result.getRecords());
        return response;
    }
}
