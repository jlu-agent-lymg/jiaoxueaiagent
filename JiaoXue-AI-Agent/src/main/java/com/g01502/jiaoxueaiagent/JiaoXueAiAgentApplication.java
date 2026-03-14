package com.g01502.jiaoxueaiagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用启动入口类。
 *
 * <p>功能说明：负责初始化 Spring Boot 容器并加载教学智能体相关组件。
 * <p>流程说明：JVM 启动后执行 main 方法 -> 调用 SpringApplication.run -> 完成自动装配与服务启动。
 */
@SpringBootApplication
public class JiaoXueAiAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(JiaoXueAiAgentApplication.class, args);
    }

}
