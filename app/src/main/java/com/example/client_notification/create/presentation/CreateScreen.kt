package com.example.client_notification.create.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.client_notification.create.presentation.componentes.NoteItem
import com.example.client_notification.ui.shared.CustomButton
import com.example.client_notification.ui.shared.ToastNotification

@Composable
fun CreateScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        NoteItem{}
        CustomButton(
            modifier = Modifier.padding(top = 16.dp),
            text = "Ordenar",
            onClick = { /* Acción de login */ }
        )
        ToastNotification()
    }

}