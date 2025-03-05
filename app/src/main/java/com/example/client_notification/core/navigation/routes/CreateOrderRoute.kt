//package com.example.client_notification.core.navigation.routes
//
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.composable
//import androidx.compose.ui.platform.LocalContext
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.client_notification.core.navigation.destinations.Destinations
//import com.example.client_notification.orderCreate.presentation.OrderCreateScreen
//import com.example.client_notification.orderCreate.presentation.OrderCreateViewModelFactory
//
//fun NavGraphBuilder.createOrderRoute(
//    navController: NavHostController
//) {
//    composable(route = Destinations.CreateOrder.route) {
//        OrderCreateScreen(
//            viewModel = viewModel(
//                factory = OrderCreateViewModelFactory(LocalContext.current)
//            ),
//            onNavigateBack = { navController.popBackStack() }
//        )
//    }
//}