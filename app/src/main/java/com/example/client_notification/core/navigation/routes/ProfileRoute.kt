package com.example.client_notification.core.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.client_notification.core.navigation.destinations.Destinations
import com.example.client_notification.profile.presentation.ProfileScreen
import com.example.client_notification.profile.presentation.ProfileViewModelFactory

fun NavGraphBuilder.profileRoute(
    navController: NavHostController
) {
    composable(route = Destinations.Profile.route) {
        val context = LocalContext.current

        ProfileScreen(
            viewModel = viewModel(
                factory = ProfileViewModelFactory(context)
            ),
            onLogout = {
                // Navegar a la pantalla de login y limpiar la pila de navegación
                navController.navigate(Destinations.Login.route) {
                    // Eliminar todas las pantallas hasta Login
                    popUpTo(Destinations.Home.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

