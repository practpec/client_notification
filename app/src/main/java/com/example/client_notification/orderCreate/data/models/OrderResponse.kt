package com.example.client_notification.orderCreate.data.models

data class OrderResponse(
    val id: Int,
    val title: String,
    val description: String,
    val creator_id: Int
)