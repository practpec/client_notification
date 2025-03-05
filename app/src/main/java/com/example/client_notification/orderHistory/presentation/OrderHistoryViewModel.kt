//package com.example.client_notification.orderHistory.presentation
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.client_notification.orderHistory.data.repository.OrderHistoryRepository
//import com.example.client_notification.orderHistory.data.models.OrderHistoryDto
//
//import kotlinx.coroutines.launch
//
//class OrderHistoryViewModel(
//    private val orderHistoryRepository: OrderHistoryRepository,
//
//) : ViewModel() {
//
//    sealed class UiState {
//        object Initial : UiState()
//        object Loading : UiState()
//        data class Success(val tasks: List<OrderHistoryDto>) : UiState()
//        data class Error(val message: String, val errors: List<String>? = null) : UiState()
//    }
//
//    sealed class DeleteTaskState {
//        object Initial : DeleteTaskState()
//        object Loading : DeleteTaskState()
//        object Success : DeleteTaskState()
//        data class Error(val message: String) : DeleteTaskState()
//    }
//
//    private val _uiState = MutableLiveData<UiState>(UiState.Initial)
//    val uiState: LiveData<UiState> = _uiState
//
//    private val _deleteTaskState = MutableLiveData<DeleteTaskState>(DeleteTaskState.Initial)
//    val deleteTaskState: LiveData<DeleteTaskState> = _deleteTaskState
//
//    init {
//        loadTasks()
//    }
//
//    fun loadTasks() {
//        viewModelScope.launch {
//            try {
//                _uiState.value = UiState.Loading
//
//                when (val result = orderHistoryRepository.getOrders()) {
//                    is OrderHistoryRepository.Result.Success -> {
//                        _uiState.value = UiState.Success(result.data)
//                    }
//                    is OrderHistoryRepository.Result.Error.BadRequest -> {
//                        _uiState.value = UiState.Error(
//                            message = result.message,
//                            errors = result.errors
//                        )
//                    }
//                    is OrderHistoryRepository.Result.Error.NetworkError -> {
//                        _uiState.value = UiState.Error(
//                            message = "Error de conexión: ${result.message}"
//                        )
//                    }
//                    is OrderHistoryRepository.Result.Error.ServerError -> {
//                        _uiState.value = UiState.Error(
//                            message = "Error del servidor: ${result.code} - ${result.message}"
//                        )
//                    }
//
//                    is OrderHistoryRepository.Result.Error.BadRequest -> TODO()
//                    is OrderHistoryRepository.Result.Error.NetworkError -> TODO()
//                    is OrderHistoryRepository.Result.Error.ServerError -> TODO()
//                }
//            } catch (e: Exception) {
//                _uiState.value = UiState.Error(
//                    message = "Error inesperado",
//                    errors = listOf(e.message ?: "Error desconocido")
//                )
//            }
//        }
//    }
//
//    fun resetState() {
//        _uiState.value = UiState.Initial
//        _deleteTaskState.value = DeleteTaskState.Initial
//    }
//}