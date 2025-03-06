package com.example.client_notification.core.network.retrofit

import com.example.client_notification.core.storage.TokenManager
import com.example.client_notification.login.data.datasource.LoginApi
import com.example.client_notification.orderCreate.data.datasource.OrderApiCreate
import com.example.client_notification.profile.data.datasource.ProfileApi
import com.example.client_notification.register.data.datasource.RegisterApi
import com.example.client_notification.shared.data.datasource.OrdersApi
//import com.example.client_notification.taskList.data.datasource.TaskDeleteApi

object RetrofitApis {
    val retrofit = RetrofitBase.provideRetrofit()

    fun getRetrofit(): RegisterApi = retrofit.create(RegisterApi::class.java)
    fun getLoginApi(): LoginApi = retrofit.create(LoginApi::class.java)

//    // Con autenticación
    fun getOrderApi(tokenManager: TokenManager): OrderApiCreate =
        RetrofitAuth.createAuthenticatedRetrofit(tokenManager).create(OrderApiCreate::class.java)

    fun getOrdersApi(tokenManager: TokenManager): OrdersApi =
        RetrofitAuth.createAuthenticatedRetrofit(tokenManager).create(OrdersApi::class.java)

    fun getProfileApi(tokenManager: TokenManager): ProfileApi {
        return RetrofitAuth.createAuthenticatedRetrofit(tokenManager).create(ProfileApi::class.java)
    }
}