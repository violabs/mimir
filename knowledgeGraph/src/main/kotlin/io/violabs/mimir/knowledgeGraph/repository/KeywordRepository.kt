package io.violabs.mimir.knowledgeGraph.repository

import io.violabs.mimir.core.common.Loggable
import io.violabs.mimir.knowledgeGraph.domain.Keyword
import org.neo4j.ogm.session.SessionFactory
import org.springframework.stereotype.Repository

interface KeywordRepository : DataRepository<Keyword, String>

@Repository
class Neo4jKeywordRepository(sessionFactory: SessionFactory) :
    Neo4jDefaultRepository<Keyword, String>(sessionFactory, Keyword::class.java), KeywordRepository, Loggable {

}