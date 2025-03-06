package com.example.client_notification.core.navigation.routes


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.client_notification.core.navigation.destinations.Destinations
import com.example.client_notification.home.presentation.HomeScreen
import com.example.client_notification.home.presentation.HomeViewModelFactory

fun NavGraphBuilder.homeRoute(
    navController: NavHostController
) {
    composable(route = Destinations.Home.route) {
        HomeScreen(
            onNavigateToCreateOrder = {
                navController.navigate(Destinations.CreateOrder.route)
            },
            viewModel = viewModel(
                factory = HomeViewModelFactory(LocalContext.current)
            )
        )
    }
}