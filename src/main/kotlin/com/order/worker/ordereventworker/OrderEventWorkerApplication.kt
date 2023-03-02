package com.order.worker.ordereventworker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class OrderEventWorkerApplication

fun main(args: Array<String>) {
    runApplication<OrderEventWorkerApplication>(*args)
}
