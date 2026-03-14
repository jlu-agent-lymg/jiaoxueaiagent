package com.g01502.jiaoxueaiagent.config;

import com.g01502.jiaoxueaiagent.tools.FileOperationTool;
import com.g01502.jiaoxueaiagent.tools.ResourceDownloadTool;
import com.g01502.jiaoxueaiagent.tools.WebScrapingTool;
import com.g01502.jiaoxueaiagent.tools.WebSearchTool;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 工具注册配置类。
 *
 * <p>功能说明：集中注册教学智能体可调用的工具集合。
 * <p>流程说明：读取搜索 API 配置 -> 实例化文件/搜索/抓取/下载工具 -> 转换为 ToolCallback 数组供模型调用。
 */
@Configuration
public class ToolRegistration {

    @Value("${search-api.api-key}")
    private String searchApiKey;

    @Bean
    public ToolCallback[] allTools() {
        FileOperationTool fileOperationTool = new FileOperationTool();
        WebSearchTool webSearchTool = new WebSearchTool(searchApiKey);
        WebScrapingTool webScrapingTool = new WebScrapingTool();
        ResourceDownloadTool resourceDownloadTool = new ResourceDownloadTool();
        return ToolCallbacks.from(
                fileOperationTool,
                webSearchTool,
                webScrapingTool,
                resourceDownloadTool
        );
    }
}
