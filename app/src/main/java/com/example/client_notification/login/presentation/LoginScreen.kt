package com.example.client_notification.login.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.client_notification.ui.shared.CustomButton
import com.example.client_notification.ui.shared.textfields.EmailTextField
import com.example.client_notification.ui.shared.textfields.PasswordTextField
import androidx.compose.runtime.livedata.observeAsState


@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    viewModel: LoginViewModel
) {
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")
    val uiState by viewModel.uiState.observeAsState(LoginViewModel.UiState.Initial)
    val emailError by viewModel.emailError.observeAsState(null)
    val passwordError by viewModel.passwordError.observeAsState(null)

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            EmailTextField(
                value = email,
                onValueChange = { viewModel.onEmailChanged(it) },
                errorMessage = emailError
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordTextField(
                value = password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                errorMessage = passwordError
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                text = "Iniciar sesión",
                onClick = { viewModel.onLoginClick() }
            )

            // Texto para redirigir al registro
            Text(
                text = "¿No tienes una cuenta? Regístrate aquí",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .clickable { onNavigateToRegister() }
                    .padding(top = 16.dp)
            )
        }

        LaunchedEffect(uiState) {
            when (uiState) {
                is LoginViewModel.UiState.Success -> {
                    onLoginSuccess()
                }
                is LoginViewModel.UiState.Error -> {
                    snackbarHostState.showSnackbar(
                        message = (uiState as LoginViewModel.UiState.Error).message
                    )
                }
                else -> {}
            }
        }
    }
}
