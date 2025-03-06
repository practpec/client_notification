package com.example.client_notification.profile.data.models

data class ProfileResponse(
    val user: ProfileData
)

data class ProfileData(
    val _id: String,
    val active: Boolean,
    val created_at: String,
    val email: String,
    val fcm_token: String?,
    val last_login: String?,
    val name: String,
    val phone: String,
    val updated_at: String
)

