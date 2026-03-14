package com.g01502.jiaoxueaiagent.agent;


import com.alibaba.cloud.ai.graph.CompileConfig;
import com.alibaba.cloud.ai.graph.RunnableConfig;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.agent.interceptor.todolist.TodoListInterceptor;
import com.alibaba.cloud.ai.graph.agent.interceptor.toolselection.ToolSelectionInterceptor;
import com.alibaba.cloud.ai.graph.checkpoint.savers.MemorySaver;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 教学智能体执行器。
 *
 * <p>功能说明：基于 ReAct 机制组装教学场景 Agent，负责多轮对话、工具调用与记忆管理。
 * <p>流程说明：合并本地工具与 MCP 工具 -> 构建 ReactAgent（提示词、拦截器、超时、存档）-> 按 threadId 执行对话。
 */
@Component
public class JiaoxueAgent {
     private static final String SYSTEM_PROMPT = "你是“多模态AI互动式教学智能体”，服务对象为一线教师。" +
             "你的首要目标不是一次性给答案，而是通过多轮对话完整理解教师教学意图：教学目标、核心知识点、讲授逻辑、重点难点、互动设计、学情差异与个性化风格。" +
             "当信息不完整时，你必须主动追问并确认关键约束（学段学科、课时长度、课堂形态、评价方式、资源限制），再进入生成阶段。" +
             "你需要支持多模态参考融合：可基于教师上传或描述的PDF教案、Word文档、图片、视频片段等，提取知识结构、案例线索与排版风格，并按教师要求融入成果。" +
             "输出应覆盖教学全流程：生成结构完整的PPT初稿、Word教案草稿、课堂互动设计（如提问链、分组任务、小游戏）、知识点动画创意，并明确每部分可直接使用或二次编辑的位置。" +
             "完成初稿后，必须引导教师进行迭代优化，按“互动-生成-反馈-再生成”闭环处理修改意见，逐轮记录变更点并给出改动理由。" +
             "整体表达要专业、清晰、可执行，兼顾减负增效与教学质量提升，确保结果真正帮助教师从事务型制作转向教学设计与创新。";

     private final ReactAgent agent;

     public JiaoxueAgent(ToolCallback[] toolCallbacks,
                         @Autowired(required = false)
                         ToolCallbackProvider toolCallbackProvider,
                         ChatModel dashscopeChatModel) {
          ToolCallback[] mergedTools = Stream.concat(
                          Arrays.stream(toolCallbacks == null ? new ToolCallback[0] : toolCallbacks),
                          Arrays.stream(toolCallbackProvider == null ? new ToolCallback[0] : toolCallbackProvider.getToolCallbacks())
                  )
                  .filter(Objects::nonNull)
                  .toArray(ToolCallback[]::new);

          this.agent = ReactAgent.builder()
                  .name("教学助手AI Agent")
                  .model(dashscopeChatModel)
                  .instruction(SYSTEM_PROMPT)
                  .tools(mergedTools)
                  // 限制 ReAct 递归轮次，避免在复杂提示词下无限规划
                  .compileConfig(CompileConfig.builder().recursionLimit(60).build())
                  // 限制单次工具执行耗时，避免外部工具长时间阻塞
                  .toolExecutionTimeout(Duration.ofSeconds(180))
                  .saver(new MemorySaver())
                  .interceptors(
                          TodoListInterceptor.builder().build(),
                          ToolSelectionInterceptor.builder()
                                  .selectionModel(dashscopeChatModel)
                                  .maxTools(3)
                                  .build()
                  )
                  .build();
     }

     public ReactAgent getAgent() {
          return agent;
     }

     /**
      * 跨请求多轮对话：相同 threadId 复用同一会话上下文。
      */
     public String chat(String message, String threadId) {
          String effectiveThreadId = (threadId == null || threadId.isBlank()) ? "default-thread" : threadId;
          RunnableConfig runnableConfig = RunnableConfig.builder()
                  .threadId(effectiveThreadId)
                  .build();
          try {
               return agent.call(message, runnableConfig).getText();
          } catch (Exception e) {
               throw new IllegalStateException("Agent call failed for threadId=" + effectiveThreadId, e);
          }
     }

}
