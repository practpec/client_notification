package com.example.client_notification.orderHistory.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.client_notification.core.storage.TokenManager
import com.example.client_notification.shared.data.repository.OrdersRepository

class OrderHistoryViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderHistoryViewModel::class.java)) {
            val tokenManager = TokenManager(context)
            val ordersRepository = OrdersRepository(tokenManager)
            @Suppress("UNCHECKED_CAST")
            return OrderHistoryViewModel(
                ordersRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}