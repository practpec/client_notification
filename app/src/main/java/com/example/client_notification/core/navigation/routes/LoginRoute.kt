package com.example.client_notification.core.navigation.routes

import android.app.Application
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.client_notification.core.navigation.destinations.Destinations
import com.example.client_notification.login.presentation.LoginScreen
import com.example.client_notification.login.presentation.LoginViewModel
import com.example.client_notification.login.presentation.LoginViewModelFactory

fun NavGraphBuilder.loginRoute(
    navController: NavHostController
) {
    composable(route = Destinations.Login.route) {
        // Obtenemos el contexto y lo convertimos a Application
        val context = LocalContext.current
        val application = remember(context) { context.applicationContext as Application }

        // Creamos el ViewModel con la factory actualizada
        val loginViewModel: LoginViewModel = viewModel(
            factory = LoginViewModelFactory(application)
        )

        LoginScreen(
            onNavigateToRegister = {
                navController.navigate(Destinations.Register.route)
            },
            onLoginSuccess = {
                // Descomentamos y actualizamos la navegación después del login exitoso
//                navController.navigate(Destinations.TaskList.route) {
//                    // Eliminamos la pantalla de login del back stack para que
//                    // el usuario no pueda volver atrás después de iniciar sesión
//                    popUpTo(Destinations.Login.route) { inclusive = true }
//                }
            },
            viewModel = loginViewModel
        )
    }
}

