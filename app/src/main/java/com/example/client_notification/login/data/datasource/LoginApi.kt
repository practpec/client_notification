package com.example.client_notification.login.data.datasource

import com.example.client_notification.login.data.model.LoginRequest
import com.example.client_notification.login.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("api/auth/users/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}