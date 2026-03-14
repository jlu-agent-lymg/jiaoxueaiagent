package com.g01502.jiaoxueaiagent.agent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 教学 Agent 集成测试类。
 *
 * <p>功能说明：验证 ReAct Agent 在教学任务场景下的工具编排与输出稳定性。
 * <p>流程说明：构造带约束提示词 -> 调用 Agent 执行 -> 输出结果并在异常时快速失败。
 */
@SpringBootTest
class JiaoxueAgentTest {
      @Autowired
      private JiaoxueAgent jiaoxueAgent;

      @Test
      @Timeout(180)
      void testJiaoxueAgent() {
          String message = "你是教学设计助手。请执行一个轻量任务并遵守以下约束：\n" +
                  "1) 先输出 TodoList（仅 3 项），状态使用 pending/in_progress/completed。\n" +
                  "2) 必须调用 1 次本地工具获取“课堂导入、形成性评价、分层作业”的资料；禁止调用 MCP 相关工具。\n" +
                  "3) 输出一个45分钟课堂设计：包含目标、重点难点、活动流程、互动问题。\n" +
                  "4) 单独增加“工具检索结果”小节：列出工具名称、检索到的关键内容（至少 2 条）、来源链接。\n" +
                  "5) 最后输出一次 TodoList 完成态完成的打对号。\n" +
                  "6) 可以文件读写，展示详细思考过程；总字数控制在 450 字以内。";
          try {
              String answer = jiaoxueAgent.getAgent().call(message).getText();
              System.out.println(answer);
          } catch (Exception e) {
              throw new RuntimeException(e);
          }
      }
}
