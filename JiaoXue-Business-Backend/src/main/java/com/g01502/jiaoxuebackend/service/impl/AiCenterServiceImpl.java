package com.g01502.jiaoxuebackend.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.g01502.jiaoxuebackend.common.exception.BusinessException;
import com.g01502.jiaoxuebackend.dto.AiChatRequest;
import com.g01502.jiaoxuebackend.dto.AiChatResponse;
import com.g01502.jiaoxuebackend.dto.AiMessageItem;
import com.g01502.jiaoxuebackend.dto.AiSessionItem;
import com.g01502.jiaoxuebackend.service.AiCenterService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * AI 中心服务实现：通过 HTTP 代理到独立 Agent 模块，实现前后端统一接入。
 */
@Slf4j
@Service
public class AiCenterServiceImpl implements AiCenterService {

    @Value("${agent.backend.base-url:http://127.0.0.1:8000}")
    private String agentBackendBaseUrl;

    private RestClient restClient;

    @PostConstruct
    public void init() {
        // 服务启动时初始化 RestClient，确保后续每次请求都复用统一配置。
        this.restClient = RestClient.builder()
                .baseUrl(agentBackendBaseUrl)
                .build();
        log.info("AI 中心代理已启用, agentBaseUrl={}", agentBackendBaseUrl);
    }

    @Override
    public AiChatResponse chat(AiChatRequest request) {
        try {
            Map<String, Object> payload = new LinkedHashMap<>();
            payload.put("message", request.getMessage());
            if (StringUtils.hasText(request.getSessionId())) {
                payload.put("session_id", request.getSessionId());
            }

            JsonNode body = restClient.post()
                    .uri("/chat")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(payload)
                    .retrieve()
                    .body(JsonNode.class);

            JsonNode data = ensureSuccess(body, "发送 AI 消息");
            AiChatResponse response = new AiChatResponse();
            response.setSessionId(data.path("session_id").asText(""));
            response.setReply(data.path("reply").asText(""));
            response.setAgentStatus(data.path("agent_status").asText(""));
            response.setFilePath(data.path("file_path").asText(""));
            if (!data.path("slides_data").isMissingNode() && !data.path("slides_data").isNull()) {
                response.setSlidesData(data.get("slides_data"));
            }

            log.info("AI 对话成功, sessionId={}, messageLength={}",
                    response.getSessionId(), request.getMessage().length());
            return response;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw convertException("发送 AI 消息", ex);
        }
    }

    @Override
    public List<AiSessionItem> listSessions() {
        try {
            JsonNode body = restClient.get()
                    .uri("/sessions")
                    .retrieve()
                    .body(JsonNode.class);

            JsonNode data = ensureSuccess(body, "查询 AI 会话列表");
            List<AiSessionItem> sessions = new ArrayList<>();
            JsonNode sessionNodes = data.path("sessions");
            if (sessionNodes.isArray()) {
                for (JsonNode node : sessionNodes) {
                    AiSessionItem item = new AiSessionItem();
                    item.setSessionId(node.path("session_id").asText(""));
                    item.setTitle(node.path("title").asText(""));
                    item.setCreatedAt(node.path("created_at").asText(""));
                    item.setUpdatedAt(node.path("updated_at").asText(""));
                    sessions.add(item);
                }
            }
            return sessions;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw convertException("查询 AI 会话列表", ex);
        }
    }

    @Override
    public String createSession() {
        try {
            JsonNode body = restClient.post()
                    .uri("/sessions")
                    .retrieve()
                    .body(JsonNode.class);

            JsonNode data = ensureSuccess(body, "创建 AI 会话");
            String sessionId = data.path("session_id").asText("");
            if (!StringUtils.hasText(sessionId)) {
                throw new BusinessException(502, "创建 AI 会话失败：未返回 session_id");
            }
            log.info("创建 AI 会话成功, sessionId={}", sessionId);
            return sessionId;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw convertException("创建 AI 会话", ex);
        }
    }

    @Override
    public void deleteSession(String sessionId) {
        if (!StringUtils.hasText(sessionId)) {
            throw new BusinessException(400, "会话 ID 不能为空");
        }
        try {
            JsonNode body = restClient.delete()
                    .uri("/sessions/{sessionId}", sessionId)
                    .retrieve()
                    .body(JsonNode.class);
            ensureSuccess(body, "删除 AI 会话");
            log.info("删除 AI 会话成功, sessionId={}", sessionId);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw convertException("删除 AI 会话", ex);
        }
    }

    @Override
    public List<AiMessageItem> listMessages(String sessionId) {
        if (!StringUtils.hasText(sessionId)) {
            throw new BusinessException(400, "会话 ID 不能为空");
        }
        try {
            JsonNode body = restClient.get()
                    .uri("/sessions/{sessionId}/messages", sessionId)
                    .retrieve()
                    .body(JsonNode.class);

            JsonNode data = ensureSuccess(body, "查询 AI 会话消息");
            List<AiMessageItem> messages = new ArrayList<>();
            JsonNode messageNodes = data.path("messages");
            if (messageNodes.isArray()) {
                for (JsonNode node : messageNodes) {
                    AiMessageItem item = new AiMessageItem();
                    item.setRole(node.path("role").asText(""));
                    item.setContent(node.path("content").asText(""));
                    item.setCreatedAt(node.path("created_at").asText(""));
                    messages.add(item);
                }
            }
            return messages;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw convertException("查询 AI 会话消息", ex);
        }
    }

    /**
     * 统一校验 Agent 返回结构，避免前端收到半结构化数据。
     */
    private JsonNode ensureSuccess(JsonNode body, String action) {
        if (body == null) {
            throw new BusinessException(502, action + "失败：AI 服务未返回数据");
        }
        String status = body.path("status").asText("");
        if (!"success".equalsIgnoreCase(status)) {
            String detail = body.path("detail").asText(body.path("message").asText("AI 服务返回异常"));
            throw new BusinessException(502, action + "失败：" + detail);
        }
        return body;
    }

    private BusinessException convertException(String action, Exception ex) {
        if (ex instanceof RestClientResponseException responseException) {
            int statusCode = responseException.getStatusCode().value();
            String body = responseException.getResponseBodyAsString();
            log.error("{}失败, statusCode={}, responseBody={}", action, statusCode, body, responseException);
            if (statusCode == 404) {
                return new BusinessException(404, action + "失败：目标会话不存在");
            }
            return new BusinessException(502, action + "失败：Agent 服务响应异常(" + statusCode + ")");
        }
        log.error("{}失败, agentBaseUrl={}, error={}", action, agentBackendBaseUrl, ex.getMessage(), ex);
        return new BusinessException(502, action + "失败：无法连接 Agent 服务");
    }
}
