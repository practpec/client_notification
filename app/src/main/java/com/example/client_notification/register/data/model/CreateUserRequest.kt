package com.example.client_notification.register.data.model

data class CreateUserRequest(
    val name : String,
    val email: String,
    val password: String,
    val phone: String,
    val fcm_token: String
)