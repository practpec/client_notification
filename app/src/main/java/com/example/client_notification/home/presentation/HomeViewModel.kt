package com.example.client_notification.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.client_notification.shared.data.repository.OrdersRepository
import com.example.client_notification.shared.data.models.OrdersDto
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeRepository: OrdersRepository
) : ViewModel() {

    sealed class UiState {
        object Initial : UiState()
        object Loading : UiState()
        data class Success(val orders: List<OrdersDto>) : UiState()
        data class Error(val message: String, val errors: List<String>? = null) : UiState()
    }

    private val _uiState = MutableLiveData<UiState>(UiState.Initial)
    val uiState: LiveData<UiState> = _uiState

    fun loadOrders() {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading

                when (val result = homeRepository.getPendingOrders()) {
                    is OrdersRepository.Result.Success -> {
                        _uiState.value = UiState.Success(result.data)
                    }
                    is OrdersRepository.Result.Error.BadRequest -> {
                        _uiState.value = UiState.Error(
                            message = result.message,
                            errors = result.errors
                        )
                    }
                    is OrdersRepository.Result.Error.NetworkError -> {
                        _uiState.value = UiState.Error(
                            message = "Error de conexión: ${result.message}"
                        )
                    }
                    is OrdersRepository.Result.Error.ServerError -> {
                        _uiState.value = UiState.Error(
                            message = "Error del servidor: ${result.code} - ${result.message}"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    message = "Error inesperado",
                    errors = listOf(e.message ?: "Error desconocido")
                )
            }
        }
    }
}

