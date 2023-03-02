package com.order.worker.ordereventworker.domain

import com.order.worker.ordereventworker.web.dto.OrderItemInfo
import javax.annotation.processing.Generated
import javax.persistence.*

@Entity
@Table(name="order_items")
class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ? = null,

    @ManyToOne
    val order: Order,
    val partnerId: Long,
    val itemId: Long,
    val itemName: String,
    val itemToken: String,
    val itemPrice: Long,
    val orderCount: Int,

    @Enumerated(EnumType.STRING)
    val deliveryStatus: DeliveryStatus,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderItem", cascade = [CascadeType.PERSIST])
    var orderItemOptionGroups : MutableList<OrderItemOptionGroup> = mutableListOf(),
) : BaseEntity () {
    enum class DeliveryStatus {
        BEFORE_DELIVERY,
        DELIVERY_PREPARE,
        DELIVERING,
        COMPLETE_DELIVERY
        ;
        companion object {
            operator fun invoke(status: String) = valueOf(status.uppercase())
        }
    }

    companion object {
        fun of(order: Order, orderItemInfo: OrderItemInfo): OrderItem {
            return OrderItem(
                order = order,
                partnerId = orderItemInfo.partnerId,
                itemId = orderItemInfo.itemId,
                itemName = orderItemInfo.itemName,
                itemToken = orderItemInfo.itemToken,
                itemPrice = orderItemInfo.itemPrice,
                orderCount = orderItemInfo.orderCount,
                deliveryStatus = DeliveryStatus.DELIVERY_PREPARE
            )
        }
    }
}