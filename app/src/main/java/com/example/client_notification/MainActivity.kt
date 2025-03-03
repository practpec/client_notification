package com.example.client_notification

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import com.example.client_notification.create.presentation.CreateScreen
import com.example.client_notification.ui.theme.Client_notificationTheme
import com.example.client_notification.login.presentation.LoginScreen
import com.example.client_notification.register.presentation.RegisterScreen
import com.example.client_notification.home.presentation.HomeScreen
import com.example.client_notification.profile.presentation.ProfileScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Client_notificationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //LoginScreen()
                    //RegisterScreen()
                    //HomeScreen()
                    //CreateScreen()
                    //ProfileScreen()
                    //HistoryScreen
                }
            }
        }
    }
}
