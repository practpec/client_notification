package com.example.client_notification.shared.data.datasource

import com.example.client_notification.shared.data.models.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface OrdersApi {
    @GET("api/orders/")
    suspend fun getOrders(): Response<ApiResponse>
}

