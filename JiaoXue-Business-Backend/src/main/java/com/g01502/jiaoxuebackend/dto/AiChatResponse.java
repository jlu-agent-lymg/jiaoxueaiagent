package com.g01502.jiaoxuebackend.dto;

import lombok.Data;

/**
 * AI 对话返回结果。
 */
@Data
public class AiChatResponse {

    /**
     * 本次对话使用的会话 ID。
     */
    private String sessionId;

    /**
     * AI 回复内容。
     */
    private String reply;

    /**
     * Agent 状态（如 generated / modified / idle）。
     */
    private String agentStatus;

    /**
     * 导出文件路径（若 Agent 生成了文件）。
     */
    private String filePath;

    /**
     * 课件预览数据，结构由 Agent 服务决定，前端按需展示。
     */
    private Object slidesData;
}
