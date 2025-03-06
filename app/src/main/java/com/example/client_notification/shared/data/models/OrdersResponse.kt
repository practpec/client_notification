package com.example.client_notification.shared.data.models

data class OrdersResponse(
    val _id: String,
    val status: String,
    val created_at: String,
    val address: String,
    val notes: String,
    val user_info: UserInfo
)

data class UserInfo(
    val name: String,
    val email: String,
    val phone: String
)

