package com.example.client_notification.orderCreate.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.client_notification.orderCreate.data.repository.OrderCreateRepository
import kotlinx.coroutines.launch

class OrderCreateViewModel(
    private val orderCreateRepository: OrderCreateRepository
) : ViewModel() {

    sealed class UiState {
        object Initial : UiState()
        object Loading : UiState()
        data class Success(val message: String) : UiState()
        data class Error(val message: String, val errors: List<String>? = null) : UiState()
    }

    private val _uiState = MutableLiveData<UiState>(UiState.Initial)
    val uiState: LiveData<UiState> = _uiState

    private val _notes = MutableLiveData("")
    val notes: LiveData<String> = _notes

    private val _address = MutableLiveData("")
    val address: LiveData<String> = _address

    private val _notesError = MutableLiveData<String?>(null)
    val notesError: LiveData<String?> = _notesError

    private val _addressError = MutableLiveData<String?>(null)
    val addressError: LiveData<String?> = _addressError

    fun onNotesChanged(newNotes: String) {
        _notes.value = newNotes
        _notesError.value = null
    }

    fun onAddressChanged(newAddress: String) {
        _address.value = newAddress
        _addressError.value = null
    }

    fun onCreateOrderClick() {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading

                val notes = _notes.value ?: ""
                val address = _address.value ?: ""


                val validationErrors = mutableListOf<String>()

                if (notes.isEmpty()) {
                    validationErrors.add("La nota es requerida")
                    _notesError.value = "La nota es requerida"
                }
                if (address.isEmpty()) {
                    validationErrors.add("La direccion es requerida")
                    _addressError.value = "La direccion es requerida"
                }

                if (validationErrors.isNotEmpty()) {
                    _uiState.value = UiState.Error(
                        message = "Por favor, complete todos los campos",
                        errors = validationErrors
                    )
                    return@launch
                }

                when (val result = orderCreateRepository.createOrder(notes, address)) {
                    is OrderCreateRepository.Result.Success -> {
                        _uiState.value = UiState.Success("Orden pedida exitosamente")
                        clearFields()
                    }
                    is OrderCreateRepository.Result.Error.BadRequest -> {
                        _uiState.value = UiState.Error(
                            message = result.message,
                            errors = result.errors
                        )
                    }
                    is OrderCreateRepository.Result.Error.NetworkError -> {
                        _uiState.value = UiState.Error(
                            message = "Error de conexión: ${result.message}"
                        )
                    }
                    is OrderCreateRepository.Result.Error.ServerError -> {
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

    private fun clearFields() {
        _notes.value = ""
        _address.value = ""
        _notesError.value = null
        _addressError.value = null
    }

    fun resetState() {
        _uiState.value = UiState.Initial
        clearFields()
    }
}