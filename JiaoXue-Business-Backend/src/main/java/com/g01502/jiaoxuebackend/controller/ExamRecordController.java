package com.g01502.jiaoxuebackend.controller;

import com.g01502.jiaoxuebackend.common.model.ApiResponse;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.ExamSubmitRequest;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.ExamRecordEntity;
import com.g01502.jiaoxuebackend.service.ExamRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 考试记录接口。
 */
@RestController
@RequestMapping("/api/exam/records")
@RequiredArgsConstructor
public class ExamRecordController {

    private final ExamRecordService examRecordService;

    @PostMapping("/submit")
    public ApiResponse<Long> submitExam(@Valid @RequestBody ExamSubmitRequest request) {
        return ApiResponse.success("提交考试成功", examRecordService.submitExam(request));
    }

    @GetMapping("/page")
    public ApiResponse<PageResponse<ExamRecordEntity>> pageRecords(@Valid PageRequest request) {
        return ApiResponse.success(examRecordService.pageRecords(request));
    }
}
