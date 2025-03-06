package com.example.client_notification.orderCreate.data.datasource

import com.example.client_notification.orderCreate.data.models.CreateOrderRequest
import com.example.client_notification.orderCreate.data.models.OrderResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderApiCreate {
    @POST("api/orders/")
    suspend fun createOrder(@Body request: CreateOrderRequest): Response<OrderResponse>
}