package com.example.client_notification.register.presentation

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
import com.example.client_notification.ui.shared.textfields.PhoneNumberTextField

@Composable
fun RegisterScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Registro",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        EmailTextField(
            value = email,
            onValueChange = { email = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextField(
            value = password,
            onValueChange = { password = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PhoneNumberTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(
            text = "Registrar",
            onClick = {  }
        )

        Text(
            text = "¿Ya tienes una cuenta? Inicia sesión aquí",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .clickable {  }
                .padding(top = 16.dp)
        )
    }
}