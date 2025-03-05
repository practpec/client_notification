package com.example.client_notification.core.navigation.routes


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.client_notification.core.navigation.destinations.Destinations
import com.example.client_notification.register.presentation.RegisterScreen

fun NavGraphBuilder.registerRoute(
    navController: NavHostController
) {
    composable(route = Destinations.Register.route) {
        RegisterScreen(
            viewModel = viewModel(),
            onNavigateToLogin = {
                navController.navigate(Destinations.Login.route) {
                    popUpTo(Destinations.Register.route) { inclusive = true }
                }
            }
        )
    }
}