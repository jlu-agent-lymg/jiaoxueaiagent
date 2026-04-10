package com.g01502.jiaoxuebackend.controller;

import com.g01502.jiaoxuebackend.common.model.ApiResponse;
import com.g01502.jiaoxuebackend.dto.AiChatRequest;
import com.g01502.jiaoxuebackend.dto.AiChatResponse;
import com.g01502.jiaoxuebackend.dto.AiMessageItem;
import com.g01502.jiaoxuebackend.dto.AiSessionItem;
import com.g01502.jiaoxuebackend.service.AiCenterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AI 中心接口：把独立 Agent 模块能力统一挂载到业务后端网关下。
 */
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiCenterController {

    private final AiCenterService aiCenterService;

    @PostMapping("/chat")
    public ApiResponse<AiChatResponse> chat(@Valid @RequestBody AiChatRequest request) {
        return ApiResponse.success("AI 回复成功", aiCenterService.chat(request));
    }

    @GetMapping("/sessions")
    public ApiResponse<List<AiSessionItem>> listSessions() {
        return ApiResponse.success(aiCenterService.listSessions());
    }

    @PostMapping("/sessions")
    public ApiResponse<String> createSession() {
        return ApiResponse.success("创建会话成功", aiCenterService.createSession());
    }

    @DeleteMapping("/sessions/{sessionId}")
    public ApiResponse<Void> deleteSession(@PathVariable("sessionId") String sessionId) {
        aiCenterService.deleteSession(sessionId);
        return ApiResponse.success("删除会话成功", null);
    }

    @GetMapping("/sessions/{sessionId}/messages")
    public ApiResponse<List<AiMessageItem>> listMessages(@PathVariable("sessionId") String sessionId) {
        return ApiResponse.success(aiCenterService.listMessages(sessionId));
    }
}
