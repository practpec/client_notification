package com.example.client_notification.orderHistory.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.client_notification.ui.shared.EmptyState
import com.example.client_notification.ui.shared.RecentOrderCard
import com.example.client_notification.shared.data.models.OrdersDto
@Composable
fun OrderHistoryScreen(
    viewModel: OrderHistoryViewModel,
) {
    val uiState by viewModel.uiState.observeAsState(OrderHistoryViewModel.UiState.Initial)
    var selectedOrder by remember { mutableStateOf<OrdersDto?>(null) }
    var showModal by remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        if (uiState is OrderHistoryViewModel.UiState.Initial || uiState is OrderHistoryViewModel.UiState.Error) {
            viewModel.loadOrders()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Historial de Pedidos",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        when (uiState) {
            is OrderHistoryViewModel.UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is OrderHistoryViewModel.UiState.Success -> {
                val orders = (uiState as OrderHistoryViewModel.UiState.Success).orders
                if (orders.isEmpty()) {
                    EmptyState(message = "No se encontraron pedidos")
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(orders) { order ->
                            RecentOrderCard(
                                order = order,
                                onClick = {
                                    selectedOrder = order
                                    showModal = true
                                }
                            )
                        }
                    }
                }
            }
            is OrderHistoryViewModel.UiState.Error -> {
                val error = (uiState as OrderHistoryViewModel.UiState.Error)
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error: ${error.message}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadOrders() }) {
                            Text("Reintentar")
                        }
                    }
                }
            }
            else -> {}
        }
    }

    if (showModal && selectedOrder != null) {
        AlertDialog(
            onDismissRequest = { showModal = false },
            title = { Text("Detalles del pedido") },
            text = {
                Column {
                    Text("Cliente: ${selectedOrder?.userName}")
                    Text("Email: ${selectedOrder?.userEmail}")
                    Text("Teléfono: ${selectedOrder?.userPhone}")
                    Text("Dirección: ${selectedOrder?.address}")
                    Text("Estado: ${selectedOrder?.status}")
                    Text("Fecha: ${selectedOrder?.date}")
                    if (!selectedOrder?.notes.isNullOrEmpty()) {
                        Text("Notas: ${selectedOrder?.notes}")
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showModal = false }) {
                    Text("Cerrar")
                }
            }
        )
    }
}

