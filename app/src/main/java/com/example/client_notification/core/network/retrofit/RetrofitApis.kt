package com.example.client_notification.core.network.retrofit

import com.example.client_notification.core.storage.TokenManager
import com.example.client_notification.login.data.datasource.LoginApi
import com.example.client_notification.register.data.datasource.RegisterApi
import com.example.client_notification.orderCreate.data.datasource.OrderApiCreate
//import com.example.client_notification.taskList.data.datasource.TaskDeleteApi
import com.example.client_notification.orderHistory.data.datasource.OrderHistoryApi

object RetrofitApis {
    val retrofit = RetrofitBase.provideRetrofit()

    fun getRetrofit(): RegisterApi = retrofit.create(RegisterApi::class.java)
    fun getLoginApi(): LoginApi = retrofit.create(LoginApi::class.java)

//    // Con autenticación
//    fun getOrderApi(tokenManager: TokenManager): OrderApiCreate =
//        RetrofitAuth.createAuthenticatedRetrofit(tokenManager).create(OrderApiCreate::class.java)
//
//    fun getOrderHistoryApi(tokenManager: TokenManager): OrderHistoryApi =
//        RetrofitAuth.createAuthenticatedRetrofit(tokenManager).create(OrderHistoryApi::class.java)
////
//    fun getTaskDeleteApi(tokenManager: TokenManager): TaskDeleteApi {
//        return RetrofitAuth.createAuthenticatedRetrofit(tokenManager).create(TaskDeleteApi::class.java)
//    }
}