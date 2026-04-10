package com.g01502.jiaoxuebackend.service;

import com.g01502.jiaoxuebackend.dto.AiChatRequest;
import com.g01502.jiaoxuebackend.dto.AiChatResponse;
import com.g01502.jiaoxuebackend.dto.AiMessageItem;
import com.g01502.jiaoxuebackend.dto.AiSessionItem;

import java.util.List;

/**
 * AI 中心服务接口，负责对接外部 Agent 模块。
 */
public interface AiCenterService {

    AiChatResponse chat(AiChatRequest request);

    List<AiSessionItem> listSessions();

    String createSession();

    void deleteSession(String sessionId);

    List<AiMessageItem> listMessages(String sessionId);
}
