package com.g01502.jiaoxuebackend.dto;

import lombok.Data;

/**
 * AI 会话摘要信息。
 */
@Data
public class AiSessionItem {

    private String sessionId;
    private String title;
    private String createdAt;
    private String updatedAt;
}
