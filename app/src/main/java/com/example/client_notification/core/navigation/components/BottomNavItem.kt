package com.example.client_notification.core.navigation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object OrderHistory : BottomNavItem("order_history", Icons.Default.History, "Historial")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Perfil")
}