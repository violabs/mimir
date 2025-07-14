package io.violabs.mimir.kafka.schemaregistry.avro.domain

import org.apache.avro.Schema
import org.apache.avro.specific.SpecificRecord

class TaskEvent : SpecificRecord {
    override fun put(i: Int, v: Any?) {
        TODO("Not yet implemented")
    }

    override fun get(i: Int): Any? {
        TODO("Not yet implemented")
    }

    override fun getSchema(): Schema? {
        TODO("Not yet implemented")
    }
}