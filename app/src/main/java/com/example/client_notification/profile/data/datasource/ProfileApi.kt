package com.example.client_notification.profile.data.datasource

import com.example.client_notification.profile.data.models.ProfileResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProfileApi {
    @GET("api/auth/profile")
    suspend fun getUserProfile(): Response<ProfileResponse>
}

