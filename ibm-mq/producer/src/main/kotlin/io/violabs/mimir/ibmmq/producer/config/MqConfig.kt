package io.violabs.mimir.ibmmq.producer.config

import com.ibm.mq.jakarta.jms.MQConnectionFactory
import com.ibm.msg.client.jakarta.wmq.WMQConstants
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.connection.CachingConnectionFactory
import org.springframework.jms.core.JmsTemplate

@Configuration
@EnableJms
class MqConfig {

    @Value("\${ibm.mq.queueManager}")
    private lateinit var queueManager: String

    @Value("\${ibm.mq.channel}")
    private lateinit var channel: String

    @Value("\${ibm.mq.connName}")
    private lateinit var connName: String

    @Value("\${ibm.mq.user}")
    private lateinit var user: String

    @Value("\${ibm.mq.password}")
    private lateinit var password: String

    @Bean
    fun mqConnectionFactory(): CachingConnectionFactory {
        val factory = MQConnectionFactory()
        factory.hostName = connName.split("(")[0]
        factory.port = connName.split("(")[1].replace(")", "").toInt()
        factory.queueManager = queueManager
        factory.channel = channel
        factory.transportType = WMQConstants.WMQ_CM_CLIENT
        factory.ccsid = 1208

        return CachingConnectionFactory(factory)
    }

    @Bean
    fun jmsTemplate(connectionFactory: CachingConnectionFactory): JmsTemplate {
        return JmsTemplate(connectionFactory)
    }
}
