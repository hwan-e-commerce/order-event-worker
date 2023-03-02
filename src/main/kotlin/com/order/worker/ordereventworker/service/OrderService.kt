package com.order.worker.ordereventworker.service

import com.order.worker.ordereventworker.common.OrderFactory
import com.order.worker.ordereventworker.repository.OrderRepository
import com.order.worker.ordereventworker.web.dto.OrderMessage
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository
) {
    @Transactional
    fun saveOrder(orderMessage: OrderMessage) {
        val initOrder = OrderFactory.createOrderFromOrderMessage(orderMessage)
        print(initOrder)
        orderRepository.save(initOrder)
    }
}