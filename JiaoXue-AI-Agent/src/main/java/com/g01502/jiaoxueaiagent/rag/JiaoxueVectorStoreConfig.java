package com.g01502.jiaoxueaiagent.rag;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


/**
 * 教学知识库向量存储配置类。
 *
 * <p>功能说明：将教学文档向量化并装载为 VectorStore，供 RAG 问答使用。
 * <p>流程说明：读取 Markdown 文档 -> 使用嵌入模型构建 SimpleVectorStore -> 写入文档向量并注册 Bean。
 */
@Configuration
@Slf4j
public class JiaoxueVectorStoreConfig {

    @Resource
    private JiaoXueDocumentLoader jiaoxueAppDocumentLoader;

    @Bean
    VectorStore JiaoxueVectorStore(EmbeddingModel dashscopeEmbeddingModel) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(dashscopeEmbeddingModel)
                .build();
        // 加载文档并写入向量库；若初始化失败，则降级为空知识库启动
        List<Document> documents = jiaoxueAppDocumentLoader.loadMarkdowns();
        if (documents == null || documents.isEmpty()) {
            log.warn("RAG 文档为空，系统将以空知识库模式启动。");
            return simpleVectorStore;
        }
        try {
            simpleVectorStore.add(documents);
        } catch (Exception e) {
            log.error("RAG 向量库初始化失败，将以空知识库模式启动。请检查 DashScope API Key。", e);
        }
        return simpleVectorStore;
    }
}
