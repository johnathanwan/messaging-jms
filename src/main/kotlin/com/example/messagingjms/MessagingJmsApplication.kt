package com.example.messagingjms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.config.*
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.support.converter.*
import javax.jms.ConnectionFactory

@SpringBootApplication
@EnableJms
class MessagingJmsApplication {

    @Bean
    fun myFactory(connectionFactory: ConnectionFactory, configurer: DefaultJmsListenerContainerFactoryConfigurer):
            JmsListenerContainerFactory<*> {
        val factory = DefaultJmsListenerContainerFactory()
        configurer.configure(factory, connectionFactory)
        return factory
    }

    @Bean
    fun jacksonJmsMessageConverter(): MessageConverter {
        val converter = MappingJackson2MessageConverter()
        converter.setTargetType(MessageType.TEXT)
        converter.setTypeIdPropertyName("_type")
        return converter
    }
}

fun main(args: Array<String>) {
    val context = runApplication<MessagingJmsApplication>(*args)
    val jmsTemplate = context.getBean(JmsTemplate::class.java)

    println("Sending an email message.")
    jmsTemplate.convertAndSend("mailbox", Email("info@example.com", "Hello"))
}
