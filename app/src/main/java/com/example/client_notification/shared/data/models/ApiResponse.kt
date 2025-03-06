package com.example.client_notification.shared.data.models

data class ApiResponse(
    val metadata: Metadata,
    val orders: List<OrdersResponse>
)

data class Metadata(
    val has_more: Boolean,
    val limit: Int,
    val skip: Int,
    val total: Int
)

