package com.example.client_notification.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.client_notification.shared.data.models.OrdersDto
import com.example.client_notification.ui.theme.Yellow


@Composable
fun RecentOrderCard(
    order: OrdersDto,
    onClick: () -> Unit
) {
    var borderColor by remember { mutableStateOf(Color.White) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                onClick()
                borderColor = Yellow
            }
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.Transparent)
        ) {
            Text(
                text = "Estado: ${order.status}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Fecha: ${order.date}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Dirección: ${order.address}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (order.notes.isNotEmpty()) {
                Text(
                    text = "Pedido: ${order.notes}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
