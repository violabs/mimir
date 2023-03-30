package io.violabs.milvus.config

import io.milvus.client.MilvusServiceClient
import io.milvus.param.ConnectParam
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MilvusConfig {
    
    @Bean
    fun milvusClient(): MilvusServiceClient =
        ConnectParam
            .newBuilder()
            .withHost("localhost")
            .withPort(19530)
            .build()
            .let(::MilvusServiceClient)
}