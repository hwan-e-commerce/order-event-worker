package com.order.worker.ordereventworker.web.dto

import com.order.worker.ordereventworker.domain.Order
import com.order.worker.ordereventworker.domain.OrderItem
import com.order.worker.ordereventworker.domain.OrderItemOption
import com.order.worker.ordereventworker.domain.OrderItemOptionGroup

data class OrderMessage(
    val orderToken: String,
    val orderNumber: String,
    val payMethod: String,
    val totalPrice: Long,
    val receiverName: String,
    val receiverPhone: String,
    val receiverZipcode: String,
    val receiverAddress1: String,
    val receiverAddress2: String,
    val etcMessage: String,
    val orderItemInfos: List<OrderItemInfo>
) {
    constructor() : this("", "", "", 0L, "","","","","","",listOf())
}

data class OrderItemInfo(
    val partnerId: Long,
    val itemId: Long,
    val itemToken: String,
    val itemName: String,
    val itemPrice: Long,
    val orderCount: Int,
    val orderItemOptionGroupInfos: List<OrderItemOptionGroupInfo>
) {
    constructor() : this(0L, 0L,"", "", 0L, 0, listOf())

    fun toEntity(order: Order): OrderItem {
//        val orderItemOptions = orderItemOptionGroupInfos.map { it. }

        val orderItem = OrderItem(
            order = order,
            partnerId = partnerId,
            itemId = itemId,
            itemName = itemName,
            itemToken = itemToken,
            itemPrice = itemPrice,
            orderCount = orderCount,
            deliveryStatus = OrderItem.DeliveryStatus.DELIVERY_PREPARE,
        )

        val orderItemOptionGroups = orderItemOptionGroupInfos
            .map { it.toEntity(orderItem) }
            .toMutableList()

        orderItem.orderItemOptionGroups = orderItemOptionGroups
        return orderItem
    }
}

data class OrderItemOptionGroupInfo(
    val ordering: Int,
    val itemOptionGroupName: String,
    val orderItemOptionInfos: List<OrderItemOptionInfo>
) {
    fun toEntity(orderItem: OrderItem): OrderItemOptionGroup {
        val orderItemOptionGroup = OrderItemOptionGroup(
            orderItem = orderItem,
            ordering = ordering,
            itemOptionGroupName = itemOptionGroupName,
        )

        val orderItemOptions = orderItemOptionInfos
            .map { it.toEntity(orderItemOptionGroup) }
            .toMutableList()

        orderItemOptionGroup.orderItemOptions = orderItemOptions

        return orderItemOptionGroup
    }
    constructor() : this(0, "", listOf())
}

data class OrderItemOptionInfo(
    val ordering: Int,
    val itemOptionName: String,
    val itemOptionPrice: Long
) {
    fun toEntity(orderItemOptionGroup: OrderItemOptionGroup) : OrderItemOption {
        return OrderItemOption(
            orderItemOptionGroup = orderItemOptionGroup,
            ordering = ordering,
            itemOptionName = itemOptionName,
            itemOptionPrice = itemOptionPrice
        )
    }

    constructor() : this(0, "", 0L)
}
