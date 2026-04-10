package com.g01502.jiaoxuebackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * AI 对话请求参数。
 */
@Data
public class AiChatRequest {

    @NotBlank(message = "消息内容不能为空")
    private String message;

    /**
     * 会话 ID，可为空；为空时由 Agent 侧自动创建会话。
     */
    private String sessionId;
}
