package com.order.worker.ordereventworker.domain

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class DeliveryFragment(
    var receiverName: String,
    var receiverPhone: String,
    var receiverZipcode: String,
    var receiverAddress1: String,
    var receiverAddress2: String,
    var etcMessage: String
)