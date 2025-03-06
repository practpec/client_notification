package com.example.client_notification.orderCreate.data.models

data class OrderResponse(
    val order: OrderData
)

data class OrderData(
    val _id: String,
    val address: String,
    val assigned_at: String?,
    val completed_at: String?,
    val courier_id: String?,
    val courier_info: Map<String, Any>,
    val created_at: String,
    val notes: String,
    val status: String,
    val updated_at: String,
    val user_id: String,
    val user_info: UserInfo
)

data class UserInfo(
    val email: String,
    val name: String,
    val phone: String
)

