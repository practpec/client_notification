package com.example.client_notification.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.client_notification.home.presentation.components.RecentOrderCard
import com.example.client_notification.ui.shared.CustomButton

@Composable
fun HomeScreen() {
    val orders = listOf(
        "2 de marzo de 2025" to "Juan Pérez",
        "3 de marzo de 2025" to "Ana García",
        "4 de marzo de 2025" to "Luis Torres",
        "5 de marzo de 2025" to "Carlos Hernández",
        "6 de marzo de 2025" to "Marta Sánchez"
    )

    var selectedOrderIndex by remember { mutableStateOf<Int?>(null) }
    var showModal by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        CustomButton(
            modifier = Modifier.padding(top = 16.dp),
            text = "Crear",
            onClick = { /* Acción de login */ }
        )

        Text(
            text = "Pedidos recientes",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)){
            orders.forEachIndexed { index, (date, receiver) ->
                RecentOrderCard(
                    date = date,
                    receiver = receiver,
                    isSelected = selectedOrderIndex == index,
                    onClick = {
                        selectedOrderIndex = if (selectedOrderIndex == index) null else index
                        showModal = selectedOrderIndex != null
                    }
                )
            }
        }
    }

    if (showModal) {
        AlertDialog(
            onDismissRequest = { showModal = false },
            title = { Text("Modal activado") },
            text = { Text("Has seleccionado un pedido. Aquí puedes mostrar más información.") },
            confirmButton = {
                TextButton(onClick = { showModal = false }) {
                    Text("Cerrar")
                }
            }
        )
    }
}
