package com.order.worker.ordereventworker.domain

import com.order.worker.ordereventworker.web.dto.OrderItemOptionInfo
import javax.persistence.*

@Entity
@Table(name = "order_item_options")
  class OrderItemOption(
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      val id: Long? = null,
      @ManyToOne(fetch = FetchType.LAZY)
      val orderItemOptionGroup: OrderItemOptionGroup,
      val ordering: Int,
      val itemOptionName: String,
      val itemOptionPrice: Long
) : BaseEntity() {
    companion object {
        fun of(orderItemOptionInfo: OrderItemOptionInfo, orderItemOptionGroup: OrderItemOptionGroup) : OrderItemOption {
            return OrderItemOption(
                orderItemOptionGroup = orderItemOptionGroup,
                ordering = orderItemOptionInfo.ordering,
                itemOptionName = orderItemOptionInfo.itemOptionName,
                itemOptionPrice = orderItemOptionInfo.itemOptionPrice
            )
        }

    }
}
