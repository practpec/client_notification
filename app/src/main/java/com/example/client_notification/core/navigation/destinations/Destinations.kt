package com.example.client_notification.core.navigation.destinations

sealed class Destinations(val route: String) {
    object Login : Destinations("login")
    object Register : Destinations("register")
    object Home : Destinations("home")
    object CreateOrder : Destinations("create_order")
    object OrderHistory : Destinations("order_history")
    object Profile : Destinations("profile")

}