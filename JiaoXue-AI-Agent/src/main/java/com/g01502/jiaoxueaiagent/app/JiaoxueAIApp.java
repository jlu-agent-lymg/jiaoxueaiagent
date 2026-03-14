package com.g01502.jiaoxueaiagent.app;

import com.g01502.jiaoxueaiagent.advisor.MyLoggerAdvisor;
import com.g01502.jiaoxueaiagent.advisor.ReReadingAdvisor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 教学对话应用编排类。
 *
 * <p>功能说明：封装统一的 ChatClient 调用入口，支持基础对话、工具调用、MCP 调用与 RAG 问答四类模式。
 * <p>流程说明：初始化系统提示词与对话记忆 -> 按场景选择 Advisor/Tool/VectorStore -> 发起请求并返回文本结果。
 */
@Component
@Slf4j
public class JiaoxueAIApp {
    @Resource
    private ToolCallback[] allTools;

    @Autowired(required = false)
    private ToolCallbackProvider toolCallbackProvider;
    private final ChatClient chatClient;

    private static final String SYSTEM_PROMPT = "你是“多模态AI互动式教学智能体”，服务对象为一线教师。" +
            "你的首要目标不是一次性给答案，而是通过多轮对话完整理解教师教学意图：教学目标、核心知识点、讲授逻辑、重点难点、互动设计、学情差异与个性化风格。" +
            "当信息不完整时，你必须主动追问并确认关键约束（学段学科、课时长度、课堂形态、评价方式、资源限制），再进入生成阶段。" +
            "你需要支持多模态参考融合：可基于教师上传或描述的PDF教案、Word文档、图片、视频片段等，提取知识结构、案例线索与排版风格，并按教师要求融入成果。" +
            "输出应覆盖教学全流程：生成结构完整的PPT初稿、Word教案草稿、课堂互动设计（如提问链、分组任务、小游戏）、知识点动画创意，并明确每部分可直接使用或二次编辑的位置。" +
            "完成初稿后，必须引导教师进行迭代优化，按“互动-生成-反馈-再生成”闭环处理修改意见，逐轮记录变更点并给出改动理由。" +
            "整体表达要专业、清晰、可执行，兼顾减负增效与教学质量提升，确保结果真正帮助教师从事务型制作转向教学设计与创新。";

    public JiaoxueAIApp(ChatModel dashscopeChatModel) {
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .maxMessages(20)
                .build();
        chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        new MyLoggerAdvisor(),
                        new ReReadingAdvisor()
                )
                .build();
    }

    public String doChat(String message, String chatId) {
        ChatResponse response = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, chatId))
                .call()
                .chatResponse();
        String content = response.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }

    public String doChatWithTools(String message, String chatId) {
        ChatResponse response = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, chatId)
                        )
                // 开启日志，便于观察效果
                .advisors(new MyLoggerAdvisor())
                .toolCallbacks(allTools)
                .call()
                .chatResponse();
        String content = response.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }

    public String doChatWithMcp(String message, String chatId) {
        if (toolCallbackProvider == null) {
            throw new IllegalStateException("MCP ToolCallbackProvider is unavailable. Enable spring.ai.mcp.client.enabled and verify MCP dependencies.");
        }
        ChatResponse response = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, chatId)
                        )
                // 开启日志，便于观察效果
                .advisors(new MyLoggerAdvisor())
                .toolCallbacks(toolCallbackProvider)
                .call()
                .chatResponse();
        String content = response.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }

    @Resource
    private VectorStore JiaoxueVectorStore;

    public String doChatWithRag(String message, String chatId) {
        ChatResponse chatResponse = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, chatId)
                        )
                // 开启日志，便于观察效果
                .advisors(new MyLoggerAdvisor())
                // 应用知识库问答
                .advisors(new QuestionAnswerAdvisor(JiaoxueVectorStore))
                .call()
                .chatResponse();
        String content = chatResponse.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }


}
