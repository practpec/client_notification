package com.example.client_notification.orderCreate.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.client_notification.orderCreate.presentation.componentes.NoteItem
import com.example.client_notification.ui.shared.CustomButton

@Composable
fun OrderCreateScreen() {
    var orderText by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        NoteItem(
            value = orderText,
            onValueChange = { orderText = it }
        )


        CustomButton(
            modifier = Modifier.padding(top = 16.dp),
            text = "Ordenar",
            onClick = { /* Acción al presionar el botón */ }
        )
    }
}