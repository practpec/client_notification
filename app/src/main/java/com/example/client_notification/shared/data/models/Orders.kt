package com.example.client_notification.shared.data.models

data class OrdersDto(
    val id: String,
    val status: String,
    val date: String,
    val address: String,
    val notes: String,
    val userName: String,
    val userEmail: String,
    val userPhone: String
)

