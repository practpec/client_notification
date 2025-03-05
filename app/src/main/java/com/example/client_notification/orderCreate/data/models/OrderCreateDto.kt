package com.example.client_notification.orderCreate.data.models

data class OrderCreateDto(
    val id: Int,
    val title: String,
    val description: String,
    val creatorId: Int
)