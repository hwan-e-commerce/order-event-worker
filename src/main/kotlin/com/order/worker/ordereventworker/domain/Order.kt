package com.order.worker.ordereventworker.domain

import com.order.worker.ordereventworker.web.dto.OrderMessage
import javax.persistence.*

@Entity
@Table(name = "orders")
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ? = null,
    val orderToken: String,
    val orderNumber: String,
    val payMethod: String,
    val totalPrice: Long,
    val status: Status,

    @Embedded
    val deliveryFragment: DeliveryFragment,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = [CascadeType.PERSIST])
    var orderItems: MutableList<OrderItem> = mutableListOf()

) : BaseEntity() {
    enum class Status {
        INIT,
        ORDER_COMPLETE,
        DELIVERY_PREPARE,
        IN_DELIVERY,
        DELIVERY_COMPLETE;

        companion object {
            operator fun invoke(status: String) = valueOf(status.uppercase());
        }
    }

    companion object {
        fun of(orderMessage: OrderMessage): Order {
            val deliveryFragment = DeliveryFragment(
                receiverName = orderMessage.receiverName,
                receiverPhone = orderMessage.receiverPhone,
                receiverZipcode = orderMessage.receiverZipcode,
                receiverAddress1 = orderMessage.receiverAddress1,
                receiverAddress2 = orderMessage.receiverAddress2,
                etcMessage = orderMessage.etcMessage
            )

            return Order(
                orderToken = orderMessage.orderToken,
                orderNumber = orderMessage.orderNumber,
                payMethod = orderMessage.payMethod,
                totalPrice = orderMessage.totalPrice,
                status = Status.INIT,
                deliveryFragment = deliveryFragment
            )
        }
    }
}

