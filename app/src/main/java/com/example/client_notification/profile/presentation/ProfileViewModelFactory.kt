package com.example.client_notification.profile.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.client_notification.core.storage.TokenManager
import com.example.client_notification.profile.data.repository.ProfileRepository

class ProfileViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            val tokenManager = TokenManager(context)
            val profileRepository = ProfileRepository(tokenManager)
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(
                profileRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}