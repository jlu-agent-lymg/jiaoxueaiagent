package com.g01502.jiaoxueaiagent.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * 教学应用层对话能力测试类。
 *
 * <p>功能说明：覆盖基础对话、工具调用、MCP 调用和 RAG 调用四类入口。
 * <p>流程说明：按不同场景构造用户请求 -> 调用对应方法 -> 对返回内容执行非空断言。
 */
@SpringBootTest
class JiaoxueAIAppTest {
    @Autowired
    private JiaoxueAIApp jiaoxueAIApp;

    @Test
    void testApp() {
        String chatId = UUID.randomUUID().toString();
        // 第一轮
        String message = "你好，我要设计一节高中信息技术课，请先帮我梳理教学目标";
        String answer = jiaoxueAIApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
        // 第二轮
        message = "请继续给出课堂重点难点和互动设计建议";
        answer = jiaoxueAIApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);

        message = "请基于以上内容生成教学流程初稿";
        answer = jiaoxueAIApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithTools() {
        // 1) 测试 WebSearchTool：联网搜索
        testMessage("请使用联网搜索工具，查询2026年中学AI素养教育的课堂实践案例，并给出5条摘要。");

        // 2) 测试 WebScrapingTool：网页抓取
        testMessage("请抓取网页 https://www.codefather.cn 的HTML内容，并提取页面标题与前200字正文概览。");

        // 3) 测试 ResourceDownloadTool：资源下载
        testMessage("请调用下载工具，把这个链接的资源下载为 teaching-banner.jpg：https://picsum.photos/1200/600");

        // 4) 测试 FileOperationTool：写文件
        testMessage("请调用文件写入工具，新建 teaching_profile.txt，内容为：学科=信息技术，学段=高中，目标=提升算法思维。");

        // 5) 测试 FileOperationTool：读文件
        testMessage("请调用文件读取工具，读取 teaching_profile.txt 并原样返回文件内容。");
    }

    private void testMessage(String message) {
        String chatId = UUID.randomUUID().toString();
        String answer = jiaoxueAIApp.doChatWithTools(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithMcp() {
        String chatId = UUID.randomUUID().toString();
        String message = "请必须调用MCP服务中的questionSearch工具，查询并返回课堂互动设计相关资源（至少5条，含简要说明）。不要直接凭记忆作答。";
        String answer = jiaoxueAIApp.doChatWithMcp(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithRag() {
        String chatId = UUID.randomUUID().toString();
        String message = "我准备一节45分钟课程，请给我一个包含导入、讲授、练习、总结的教案结构，并推荐可参考的网站地址";
        String answer = jiaoxueAIApp.doChatWithRag(message, chatId);
        Assertions.assertNotNull(answer);
    }
}
