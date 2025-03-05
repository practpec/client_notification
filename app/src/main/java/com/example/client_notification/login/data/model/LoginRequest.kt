package com.example.client_notification.login.data.model

data class LoginRequest(
    val email: String,
    val password: String,
    val fcm_token:String
)
