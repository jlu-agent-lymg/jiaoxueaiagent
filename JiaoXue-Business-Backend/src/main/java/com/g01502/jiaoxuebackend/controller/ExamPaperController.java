package com.g01502.jiaoxuebackend.controller;

import com.g01502.jiaoxuebackend.common.model.ApiResponse;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.ExamPaperEntity;
import com.g01502.jiaoxuebackend.service.ExamPaperService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 试卷管理接口。
 */
@RestController
@RequestMapping("/api/exam/papers")
@RequiredArgsConstructor
public class ExamPaperController {

    private final ExamPaperService examPaperService;

    @PostMapping
    public ApiResponse<Long> createPaper(@Valid @RequestBody ExamPaperEntity paper) {
        return ApiResponse.success("创建试卷成功", examPaperService.createPaper(paper));
    }

    @PostMapping("/{id}/publish")
    public ApiResponse<Void> publishPaper(@PathVariable("id") Long id) {
        examPaperService.publishPaper(id);
        return ApiResponse.success("发布试卷成功", null);
    }

    @GetMapping("/page")
    public ApiResponse<PageResponse<ExamPaperEntity>> pagePapers(@Valid PageRequest request) {
        return ApiResponse.success(examPaperService.pagePapers(request));
    }
}
