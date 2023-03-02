package com.order.worker.ordereventworker.domain

import com.order.worker.ordereventworker.web.dto.OrderItemOptionGroupInfo
import javax.persistence.*


@Entity
@Table(name = "order_item_option_groups")
class OrderItemOptionGroup(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    val orderItem: OrderItem,
    val ordering: Int,
    val itemOptionGroupName: String,
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderItemOptionGroup", cascade = [CascadeType.PERSIST])
    var orderItemOptions: MutableList<OrderItemOption> = mutableListOf(),
) : BaseEntity() {
    companion object {
        fun of (
            orderItem: OrderItem,
            orderItemOptionGroupInfo: OrderItemOptionGroupInfo,
            orderItemOptions: MutableList<OrderItemOption>
        ) : OrderItemOptionGroup {
            return OrderItemOptionGroup(
                orderItem = orderItem,
                ordering = orderItemOptionGroupInfo.ordering,
                itemOptionGroupName = orderItemOptionGroupInfo.itemOptionGroupName,
                orderItemOptions = orderItemOptions
            )
        }
    }
}