package com.g01502.jiaoxuebackend.dto;

import lombok.Data;

/**
 * AI 会话消息项。
 */
@Data
public class AiMessageItem {

    private String role;
    private String content;
    private String createdAt;
}
