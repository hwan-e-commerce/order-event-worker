package com.order.worker.ordereventworker.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SQSConfig(

    @Value("\${cloud.aws.credentials.access-key}")
    private val accessKey: String,

    @Value("\${cloud.aws.credentials.secret-key}")
    private val secretKey: String
) {
    @Bean
    fun amazonSQS(): AmazonSQSAsync {
        val credentialsProvider = AWSStaticCredentialsProvider(
            BasicAWSCredentials(accessKey, secretKey)
        )

        return AmazonSQSAsyncClientBuilder
            .standard()
            .withRegion(Regions.AP_NORTHEAST_2)
            .withCredentials(credentialsProvider)
            .build()
    }
}