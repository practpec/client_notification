package com.example.client_notification.orderHistory.data.datasource

import com.example.client_notification.orderHistory.data.models.OrderHistoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface OrderHistoryApi {
    @GET("tasks")
    suspend fun getOrders(): Response<List<OrderHistoryResponse>>
}