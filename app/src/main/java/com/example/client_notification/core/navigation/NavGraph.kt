package com.example.client_notification.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.client_notification.core.navigation.components.TaskNavigation
import com.example.client_notification.core.navigation.routes.*
import com.example.client_notification.core.navigation.destinations.Destinations

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Destinations.Login.route
) {
    // Obtener la ruta actual para determinar si mostrar la barra de navegación
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val showBottomBar = currentRoute in listOf(
        Destinations.Home.route,
        Destinations.OrderHistory.route,
        Destinations.Profile.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                TaskNavigation(navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
        ) {
            loginRoute(navController)
            registerRoute(navController)
            homeRoute(navController)
            createOrderRoute(navController)
            orderHistoryRoute(navController)
            profileRoute(navController)
        }
    }
}

