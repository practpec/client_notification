package com.example.client_notification.core.network.retrofit

import com.example.client_notification.core.storage.TokenManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object RetrofitAuth {

    fun createAuthenticatedRetrofit(tokenManager: TokenManager): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val token = tokenManager.getToken()
                val newRequest = if (token != null) {
                    originalRequest.newBuilder()
                        .header("Authorization", "Bearer $token")
                        .build()
                } else {
                    originalRequest
                }
                chain.proceed(newRequest)
            }
            .build()

        return RetrofitBase.buildRetrofit(client)
    }


}