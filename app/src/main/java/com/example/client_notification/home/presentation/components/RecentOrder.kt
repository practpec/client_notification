package com.example.client_notification.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.client_notification.shared.data.models.OrdersDto

@Composable
fun RecentOrderCard(
    order: OrdersDto,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "ID: ${order.id}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Estado: ${order.status}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Fecha: ${order.date}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Cliente: ${order.userName}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Dirección: ${order.address}",
                style = MaterialTheme.typography.bodySmall
            )
            if (order.notes.isNotEmpty()) {
                Text(
                    text = "Notas: ${order.notes}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

