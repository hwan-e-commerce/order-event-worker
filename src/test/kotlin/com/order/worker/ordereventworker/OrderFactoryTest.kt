package com.order.worker.ordereventworker

import com.order.worker.ordereventworker.common.OrderFactory
import com.order.worker.ordereventworker.domain.OrderItemOptionGroup
import com.order.worker.ordereventworker.web.dto.OrderItemInfo
import com.order.worker.ordereventworker.web.dto.OrderItemOptionGroupInfo
import com.order.worker.ordereventworker.web.dto.OrderItemOptionInfo
import com.order.worker.ordereventworker.web.dto.OrderMessage
import org.junit.jupiter.api.Test
import org.mockito.Spy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean


class OrderFactoryTest(
    @Spy
    private val orderFactory: OrderFactory
) {

    @Test
    fun test() {
        val orderItemOptionInfo = OrderItemOptionInfo(
            1, "test option", 1200L
        )
        val orderItemOptionInfo2 = OrderItemOptionInfo(
            2, "test option2", 1400L
        )

        val options = listOf<OrderItemOptionInfo>(orderItemOptionInfo, orderItemOptionInfo2)

        val orderItemOptionGroup = OrderItemOptionGroupInfo(
            ordering = 1,
            itemOptionGroupName = "test group",
            orderItemOptionInfos = options
        )


        val orderItemInfo = OrderItemInfo(
            partnerId = 1L,
            itemId = 1L,
            itemToken = "123dqmdkwqmeklqw",
            itemName = "test",
            itemPrice = 43000L,
            orderCount = 4,
            listOf(orderItemOptionGroup)
        )



        val orderMessage = OrderMessage(
            orderToken = "123213kmmasfdsfmweikf",
            orderNumber = "meqwkmkwqlenkwq-3129akfndsmfn",
            payMethod = "NAVER",
            totalPrice = 43000L,
            receiverName = "changhwan",
            receiverPhone = "010-3213-3213",
            receiverZipcode = "123-123",
            receiverAddress1 = "우리집",
            receiverAddress2 = "임다",
            etcMessage = "없슴다",
            orderItemInfos = listOf(orderItemInfo)
        )

        orderFactory.createOrderFromOrderMessage(orderMessage)
    }

}