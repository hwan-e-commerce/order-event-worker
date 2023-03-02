package com.order.worker.ordereventworker.repository

import com.order.worker.ordereventworker.domain.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long>