package com.example.client_notification.orderCreate.presentation.componentes

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.client_notification.ui.theme.Yellow

@Composable
fun AddressTextField(
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String? = null,  // Nuevo parámetro para manejar errores
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text("Dirección") },
            isError = errorMessage != null,  // Muestra el error si el mensaje no es nulo
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .padding(14.dp)
                .border(1.dp, if (isFocused) Yellow else Color.White, shape = MaterialTheme.shapes.small)
                .focusTarget()
                .onFocusChanged { focusState -> isFocused = focusState.isFocused },
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                errorTextColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedLabelColor = Yellow,
                unfocusedLabelColor = MaterialTheme.colorScheme.onBackground
            ),
            shape = MaterialTheme.shapes.small
        )
        // Mostrar el error debajo del campo si hay un mensaje de error
        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}