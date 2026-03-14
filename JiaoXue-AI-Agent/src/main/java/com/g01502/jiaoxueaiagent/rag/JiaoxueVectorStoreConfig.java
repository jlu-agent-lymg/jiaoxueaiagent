package com.g01502.jiaoxueaiagent.rag;

import jakarta.annotation.Resource;
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
public class JiaoxueVectorStoreConfig {

    @Resource
    private JiaoXueDocumentLoader jiaoxueAppDocumentLoader;

    @Bean
    VectorStore JiaoxueVectorStore(EmbeddingModel dashscopeEmbeddingModel) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(dashscopeEmbeddingModel)
                .build();
        // 加载文档
        List<Document> documents = jiaoxueAppDocumentLoader.loadMarkdowns();
        simpleVectorStore.add(documents);
        return simpleVectorStore;
    }
}
