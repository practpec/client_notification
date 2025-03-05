package com.example.client_notification.core.navigation.destinations


sealed class Destinations(val route: String) {
    object Login : Destinations("login")
    object Register : Destinations("register")
//    object CreateOrder : Destinations("create_order")
//    object TaskList : Destinations("task_list")

}