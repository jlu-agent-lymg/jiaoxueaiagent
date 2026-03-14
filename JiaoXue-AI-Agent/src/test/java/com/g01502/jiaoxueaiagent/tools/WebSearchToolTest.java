package com.g01502.jiaoxueaiagent.tools;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 联网搜索工具测试类。
 *
 * <p>功能说明：验证 SearchAPI 配置与 WebSearchTool 的基本可用性。
 * <p>流程说明：注入 API Key -> 创建工具实例 -> 发起检索请求 -> 校验返回结果非空。
 */
@SpringBootTest
public class WebSearchToolTest {

    @Value("${search-api.api-key}")
    private String searchApiKey;

    @Test
    public void testSearchWeb() {
        WebSearchTool tool = new WebSearchTool(searchApiKey);
        String query = "程序员鱼皮编程导航 codefather.cn";
        String result = tool.searchWeb(query);
        System.out.println(result);
        assertNotNull(result);
    }
}
