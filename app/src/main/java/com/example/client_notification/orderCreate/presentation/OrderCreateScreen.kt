package com.example.client_notification.orderCreate.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.client_notification.core.storage.TokenManager
import com.example.client_notification.orderCreate.presentation.componentes.AddressTextField
import com.example.client_notification.orderCreate.presentation.componentes.CreateButton
import com.example.client_notification.orderCreate.presentation.componentes.NoteItem

@Composable
fun OrderCreateScreen(
    viewModel: OrderCreateViewModel,
    onNavigateBack: () -> Unit = {}
) {
    val notes by viewModel.notes.observeAsState("")
    val address by viewModel.address.observeAsState("")
    val uiState by viewModel.uiState.observeAsState(OrderCreateViewModel.UiState.Initial)
    val notesError by viewModel.notesError.observeAsState(null)
    val addressError by viewModel.addressError.observeAsState(null)

    val tokenManager = TokenManager(LocalContext.current)
    val currentToken = remember { tokenManager.getToken() }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        if (currentToken.isNullOrEmpty()) {
            snackbarHostState.showSnackbar("No hay token de autenticación")
            onNavigateBack()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,

                ) {
                    IconButton(
                        onClick = onNavigateBack
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                    Text(
                        text = "Regresar",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                NoteItem(
                    value = notes,
                    onValueChange = { viewModel.onNotesChanged(it) },
                    errorMessage = notesError
                )

                AddressTextField(
                    value = address,
                    onValueChange = { viewModel.onAddressChanged(it) },
                    errorMessage = addressError
                )

                CreateButton(
                    modifier = Modifier,
                    text = "Ordenar",
                    enabled = true,//uiState !is OrderCreateViewModel.UiState.Loading,
                    onClick = { viewModel.onCreateOrderClick() }
                )
            }
        }
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is OrderCreateViewModel.UiState.Success -> {
                snackbarHostState.showSnackbar("Orden creada exitosamente")
                onNavigateBack()
            }
            is OrderCreateViewModel.UiState.Error -> {
                snackbarHostState.showSnackbar((uiState as OrderCreateViewModel.UiState.Error).message)
            }
            else -> {}
        }
    }
}