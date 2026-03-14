package com.g01502.jiaoxueaiagent.constant;

/**
 * API 密钥常量定义接口。
 *
 * <p>功能说明：统一维护模型服务访问密钥常量，供配置类读取使用。
 * <p>流程说明：启动时由模型配置类引用该常量并注入到 DashScopeApi 构建流程。
 */
public interface ApiKey {
    String apiKeyNum=your_key;
}
