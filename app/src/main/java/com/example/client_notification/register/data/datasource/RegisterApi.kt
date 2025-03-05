package com.example.client_notification.register.data.datasource

import com.example.client_notification.register.data.model.CreateUserRequest
import com.example.client_notification.register.data.model.UserDTO
import com.example.client_notification.register.data.model.UsernameValidateDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RegisterApi {
    @GET("check-email/{email}")
    suspend fun validateUsername(@Path("email") usuarioId: String): Response<UsernameValidateDTO>

    @POST("api/auth/users/register")
    suspend fun createUser(@Body request: CreateUserRequest): Response<UserDTO>
}