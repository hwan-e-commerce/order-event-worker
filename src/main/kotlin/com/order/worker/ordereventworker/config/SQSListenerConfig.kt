package com.order.worker.ordereventworker.config

import com.amazonaws.services.sqs.AmazonSQSAsync
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory
import org.springframework.cloud.aws.messaging.listener.QueueMessageHandler
import org.springframework.cloud.aws.messaging.listener.SimpleMessageListenerContainer
import org.springframework.context.annotation.Bean
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Component
import java.util.Collections

@Component
class SQSListenerConfig {

    @Bean
    fun simpleMessageListenerContainerFactory(sqsAsync: AmazonSQSAsync): SimpleMessageListenerContainerFactory {
        val factory = SimpleMessageListenerContainerFactory()
        factory.setAmazonSqs(sqsAsync)
        factory.setAutoStartup(true)
        return factory
    }

    @Bean
    fun simpleMessageListenerContainer(
        simpleMessageListenerContainerFactory: SimpleMessageListenerContainerFactory,
        queueMessageHandler: QueueMessageHandler,
        messageThreadPoolTaskExecutor: ThreadPoolTaskExecutor
    ): SimpleMessageListenerContainer {
        val container = simpleMessageListenerContainerFactory.createSimpleMessageListenerContainer()
        container.setMessageHandler(queueMessageHandler)
        container.setTaskExecutor(messageThreadPoolTaskExecutor)
        return container
    }

    @Bean
    @ConditionalOnMissingBean
    fun queueMessageHandlerFactory(sqsAsync: AmazonSQSAsync): QueueMessageHandlerFactory {
        val factory = QueueMessageHandlerFactory()
        factory.setAmazonSqs(sqsAsync)
        val messageConverter = MappingJackson2MessageConverter()
        factory.setArgumentResolvers(
            Collections.singletonList(PayloadMethodArgumentResolver(messageConverter)) as List<HandlerMethodArgumentResolver>?
        )
        return factory
    }


    @Bean
    fun queMessageHandler(
        queueMessageHandlerFactory: QueueMessageHandlerFactory
    ): QueueMessageHandler {
        return queueMessageHandlerFactory.createQueueMessageHandler()
    }

    @Bean
    fun messageThreadPoolTaskExecutor(): ThreadPoolTaskExecutor {
        val taskExecutor = ThreadPoolTaskExecutor()
        taskExecutor.setThreadNamePrefix("sqs-")
        taskExecutor.corePoolSize = 8
        taskExecutor.maxPoolSize = 100
        taskExecutor.afterPropertiesSet()
        return taskExecutor
    }
}