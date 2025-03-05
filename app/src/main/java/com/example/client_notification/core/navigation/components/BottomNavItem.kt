package com.example.client_notification.core.navigation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    object Create : BottomNavItem("create_order", Icons.Default.Add, "Crear")
    object List : BottomNavItem("order_list", Icons.Default.List, "Lista")
}