package com.g01502.jiaoxuebackend.controller;

import com.g01502.jiaoxuebackend.common.model.ApiResponse;
import com.g01502.jiaoxuebackend.common.model.PageResponse;
import com.g01502.jiaoxuebackend.dto.NoticePublishRequest;
import com.g01502.jiaoxuebackend.dto.PageRequest;
import com.g01502.jiaoxuebackend.entity.NoticeEntity;
import com.g01502.jiaoxuebackend.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 公告管理接口。
 */
@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping
    public ApiResponse<Long> publishNotice(@Valid @RequestBody NoticePublishRequest request) {
        return ApiResponse.success("发布公告成功", noticeService.publishNotice(request));
    }

    @GetMapping("/page")
    public ApiResponse<PageResponse<NoticeEntity>> pageNotices(@Valid PageRequest request) {
        return ApiResponse.success(noticeService.pageNotices(request));
    }
}
