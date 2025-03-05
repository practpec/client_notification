//package com.example.client_notification.orderCreate.presentation
//
//import android.content.Context
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.client_notification.core.storage.TokenManager
//import com.example.client_notification.orderCreate.data.repository.OrderCreateRepository
//import com.example.client_notification.orderCreate.presentation.OrderCreateViewModel
//
//class OrderCreateViewModelFactory(
//    private val context: Context
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(OrderCreateViewModel::class.java)) {
//            val tokenManager = TokenManager(context)
//            val taskCreateRepository = OrderCreateRepository(tokenManager)
//            @Suppress("UNCHECKED_CAST")
//            return OrderCreateViewModel(taskCreateRepository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}