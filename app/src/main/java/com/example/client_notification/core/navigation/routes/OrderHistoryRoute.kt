package com.example.client_notification.core.navigation.routes


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.client_notification.core.navigation.destinations.Destinations
import com.example.client_notification.orderHistory.presentation.OrderHistoryScreen
import com.example.client_notification.orderHistory.presentation.OrderHistoryViewModelFactory

fun NavGraphBuilder.orderHistoryRoute(
    navController: NavHostController
) {
    composable(route = Destinations.OrderHistory.route) {
        OrderHistoryScreen(
            viewModel = viewModel(
                factory = OrderHistoryViewModelFactory(LocalContext.current)
            )
        )
    }
}