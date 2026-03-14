package com.g01502.jiaoxueaiagent.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.g01502.jiaoxueaiagent.constant.ApiKey;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DashScope 聊天模型配置类。
 *
 * <p>功能说明：创建统一的 {@link ChatModel} Bean，供应用内 Agent 与 ChatClient 复用。
 * <p>流程说明：读取 API Key -> 构建 DashScopeApi -> 配置模型参数（温度、输出长度、采样）-> 注入 Spring 容器。
 */
@Configuration
public class DashscopeChatModelConfig {
    @Bean
    public ChatModel dashscopeChatModel() {
        DashScopeApi dashScopeApi = DashScopeApi.builder()
                .apiKey(ApiKey.apiKeyNum)
                .build();

// 创建 ChatModel
       return DashScopeChatModel.builder()
                .dashScopeApi(dashScopeApi)
                .defaultOptions(DashScopeChatOptions.builder()
                        .withModel(DashScopeChatModel.DEFAULT_MODEL_NAME)
                        .withTemperature(0.7)    // 控制随机性
                        .withMaxToken(10000)      // 最大输出长度
                        .withTopP(0.9)// 核采样参数
                        .build())
                .build();
    }
}
