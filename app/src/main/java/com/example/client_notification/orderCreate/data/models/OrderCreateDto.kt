package com.example.client_notification.orderCreate.data.models

data class OrderCreateDto(
    val id: String,
    val notes: String,
    val address: String,
    val status: String,
    val createdAt: String
)

