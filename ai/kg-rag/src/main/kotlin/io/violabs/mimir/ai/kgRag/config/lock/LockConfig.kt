package io.violabs.mimir.ai.kgRag.config.lock

import io.violabs.mimir.ai.kgRag.domain.entity.Topic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LockConfig {

    @Bean
    fun topicKeyedLockManager(): KeyedLockManager<Topic> = object : KeyedLockManager<Topic>() {}
}