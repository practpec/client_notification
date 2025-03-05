package com.example.client_notification.register.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import com.example.client_notification.ui.shared.CustomButton
import com.example.client_notification.ui.shared.textfields.EmailTextField
import com.example.client_notification.ui.shared.textfields.NameTextField
import com.example.client_notification.ui.shared.textfields.PasswordTextField
import com.example.client_notification.ui.shared.textfields.PhoneNumberTextField

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onNavigateToLogin: () -> Unit = {}
) {
    val name by viewModel.name.observeAsState("")
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")
    val phoneNumber by viewModel.phoneNumber.observeAsState("")
    val nameError by viewModel.nameError.observeAsState(null)
    val emailError by viewModel.emailError.observeAsState(null)
    val passwordError by viewModel.passwordError.observeAsState(null)
    val phoneNumberError by viewModel.phoneNumberError.observeAsState(null)

    // Estado del snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    // Scaffold para la pantalla
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
            // Título de la pantalla
            Text(
                text = "Registro",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Campo de email
            NameTextField(
                value = name,
                onValueChange = { viewModel.onNameChanged(it) },
                errorMessage = nameError
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de email
            EmailTextField(
                value = email,
                onValueChange = { viewModel.onEmailChanged(it) },
                errorMessage = emailError
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de contraseña
            PasswordTextField(
                value = password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                errorMessage = passwordError
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de teléfono
            PhoneNumberTextField(
                value = phoneNumber,
                onValueChange = { viewModel.onPhoneNumberChanged(it) },
                errorMessage = phoneNumberError
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de registro
            CustomButton(
                text = "Registrar",
                onClick = { viewModel.onRegisterClick() }
            )

            // Texto para redirigir al login
            Text(
                text = "¿Ya tienes una cuenta? Inicia sesión aquí",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .clickable { onNavigateToLogin() }
                    .padding(top = 16.dp)
            )
        }

        // Lógica para manejar el estado de la UI (como éxito o error)
        LaunchedEffect(viewModel.uiState.value) {
            when (val uiState = viewModel.uiState.value) {
                is RegisterViewModel.UiState.Success -> {
                    snackbarHostState.showSnackbar("Usuario registrado exitosamente")
                }
                is RegisterViewModel.UiState.Error -> {
                    snackbarHostState.showSnackbar(uiState.message)
                }
                else -> {}
            }
        }
    }
}
