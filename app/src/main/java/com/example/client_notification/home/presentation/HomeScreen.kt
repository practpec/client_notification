package com.example.client_notification.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.client_notification.home.presentation.components.RecentOrderCard
import com.example.client_notification.shared.data.models.OrdersDto
import com.example.client_notification.ui.shared.CustomButton

@Composable
fun HomeScreen(
    onNavigateToCreateOrder: () -> Unit = {},
    viewModel: HomeViewModel,
) {
    val uiState by viewModel.uiState.observeAsState(HomeViewModel.UiState.Initial)
    var selectedOrder by remember { mutableStateOf<OrdersDto?>(null) }
    var showModal by remember { mutableStateOf(false) }

    // Cargar órdenes al iniciar la pantalla
    LaunchedEffect(key1 = true) {
        viewModel.loadOrders()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        CustomButton(
            modifier = Modifier.padding(top = 16.dp),
            text = "Crear",
            onClick = { onNavigateToCreateOrder() }
        )

        Text(
            text = "Pedidos recientes",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        when (uiState) {
            is HomeViewModel.UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is HomeViewModel.UiState.Success -> {
                val orders = (uiState as HomeViewModel.UiState.Success).orders
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
            is HomeViewModel.UiState.Error -> {
                val error = (uiState as HomeViewModel.UiState.Error)
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
            else -> { /* Estado inicial, no hacer nada */ }
        }
    }

    if (showModal && selectedOrder != null) {
        AlertDialog(
            onDismissRequest = { showModal = false },
            title = { Text("Detalles del pedido") },
            text = {
                Column {
                    Text("ID: ${selectedOrder?.id}")
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

