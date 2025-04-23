package io.violabs.mimir.ai.kgRag.config.lock

import com.github.benmanes.caffeine.cache.Caffeine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.TimeUnit

open class KeyedLockManager<K> {
    private val cache = Caffeine.newBuilder()
        .expireAfterAccess(10, TimeUnit.MINUTES)
        .weakKeys()
        .weakValues()
        .build<K, Mutex>()

    suspend fun <T> withLock(key: K, block: suspend () -> T): T {
        val mutex = cache.get(key) { Mutex() }
        val item: T = mutex.withLock { block.invoke() }
        return item
    }
}