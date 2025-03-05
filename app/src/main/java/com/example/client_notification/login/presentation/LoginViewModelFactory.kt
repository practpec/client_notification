package com.example.client_notification.login.presentation


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.client_notification.core.storage.TokenManager
import com.example.client_notification.login.data.repository.LoginRepository

class LoginViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val tokenManager = TokenManager(application.applicationContext)
            val loginRepository = LoginRepository()
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(application, loginRepository, tokenManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

