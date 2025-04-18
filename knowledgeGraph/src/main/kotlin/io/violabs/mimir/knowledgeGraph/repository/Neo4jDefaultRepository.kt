package io.violabs.mimir.knowledgeGraph.repository

import mu.two.KLogger
import mu.two.KLogging
import org.neo4j.ogm.session.SessionFactory
import java.io.Serializable

abstract class Neo4jDefaultRepository<T : Any, KEY : Serializable>(
    protected val sessionFactory: SessionFactory,
    protected val klass: Class<out T>
) : DataRepository<T, KEY> {
    protected var _log: KLogger = KLogging().logger(this::class.simpleName!!)

    override fun save(item: T) {
        try {
            val session = sessionFactory.openSession()
            session.save(item)
        } catch (e: Exception) {
            _log.error("Failed to save ${klass.simpleName}. message: ${e.message}")
        }
    }

    override fun findById(id: KEY): T? {
        return try {
            val session = sessionFactory.openSession()
            session.load(klass, id)
        } catch (e: Exception) {
            _log.error("Failed to get ${klass.simpleName}. message: ${e.message}")
            null
        }
    }
}