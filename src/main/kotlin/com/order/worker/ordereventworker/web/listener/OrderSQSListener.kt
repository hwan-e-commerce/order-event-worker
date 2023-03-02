package com.order.worker.ordereventworker.web.listener

import com.order.worker.ordereventworker.service.OrderService
import com.order.worker.ordereventworker.web.dto.OrderMessage
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.stereotype.Component

@Component
class OrderSQSListener(
    private val orderService: OrderService
) {
    @SqsListener(value = ["order-event-sqs.fifo"], deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    fun readMessage (message: OrderMessage) {

        println("message: $message")
        orderService.saveOrder(message)
    }
}