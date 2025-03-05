package com.example.client_notification.login.data.datasource

import com.example.client_notification.login.data.model.LoginRequest
import com.example.client_notification.login.data.model.LoginResponse
import retrofit2.Response

class LoginDataSource(private val api: LoginApi) {
    suspend fun login(email: String, password: String,  fcmToken: String): Response<LoginResponse> {
        return api.login(LoginRequest(email, password,  fcmToken))
    }
}