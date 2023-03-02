package com.order.worker.ordereventworker.common

import com.order.worker.ordereventworker.domain.Order
import com.order.worker.ordereventworker.domain.OrderItem
import com.order.worker.ordereventworker.domain.OrderItemOption
import com.order.worker.ordereventworker.domain.OrderItemOptionGroup
import com.order.worker.ordereventworker.web.dto.OrderMessage
import org.springframework.stereotype.Component

class OrderFactory {
    companion object {
        fun createOrderFromOrderMessage(orderMessage: OrderMessage) : Order {
            val initOrder = Order.of(orderMessage)
            val orderItemInfos = orderMessage.orderItemInfos

            val orderItems = orderItemInfos
                .map { orderItemInfo -> orderItemInfo.toEntity(initOrder) }
                .toMutableList()

            initOrder.orderItems = orderItems
            return initOrder
        }
    }
}