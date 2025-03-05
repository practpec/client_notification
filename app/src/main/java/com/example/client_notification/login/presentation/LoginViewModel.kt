package com.example.client_notification.login.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.client_notification.core.FCM.FCMService
import com.example.client_notification.core.storage.TokenManager
import com.example.client_notification.login.data.repository.LoginRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LoginViewModel(
    application: Application,
    private val loginRepository: LoginRepository,
    private val tokenManager: TokenManager
) : AndroidViewModel(application) {


    sealed class UiState {
        object Initial : UiState()
        object Loading : UiState()
        data class Success(val token: String) : UiState()
        data class Error(val message: String, val errors: List<String>? = null) : UiState()
    }

    private val _uiState = MutableLiveData<UiState>(UiState.Initial)

    private val context = application.applicationContext
    val uiState: LiveData<UiState> = _uiState

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private var fcmToken: String? = null

    private val _emailError = MutableLiveData<String?>(null)
    val emailError: LiveData<String?> = _emailError

    private val _passwordError = MutableLiveData<String?>(null)
    val passwordError: LiveData<String?> = _passwordError

    // Función para obtener el token FCM de manera suspendida (para usar con corrutinas)
    private suspend fun getFCMToken(): String = suspendCancellableCoroutine { continuation ->
        // Primero intentamos obtener el token desde nuestra clase de servicio
        FCMService.getToken()?.let {
            continuation.resume(it)
            return@suspendCancellableCoroutine
        }

        // Si no está en memoria, intentamos recuperarlo de SharedPreferences
        val sharedPrefs = context.getSharedPreferences("FCMPrefs", Context.MODE_PRIVATE)
        val savedToken = sharedPrefs.getString("fcm_token", null)

        if (!savedToken.isNullOrEmpty()) {
            // Si lo encontramos en SharedPreferences, lo guardamos en memoria y lo devolvemos
            FCMService.setToken(savedToken)
            continuation.resume(savedToken)
            return@suspendCancellableCoroutine
        }

        // Si no lo encontramos en ningún lado, lo solicitamos a Firebase
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                // Si hay un error, lo propagamos
                continuation.resumeWithException(
                    Exception("No se pudo obtener el token FCM: ${task.exception?.message}")
                )
                return@OnCompleteListener
            }

            // Obtenemos el token
            val token = task.result ?: ""

            // Lo guardamos en SharedPreferences
            with(sharedPrefs.edit()) {
                putString("fcm_token", token)
                apply()
            }

            // Lo guardamos en memoria
            FCMService.setToken(token)

            // Lo devolvemos
            continuation.resume(token)
        })
    }


    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
        _emailError.value = null
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
        _passwordError.value = null
    }

    fun onLoginClick() {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading

                delay(1500) // Delay de 1.5 segundos

                val email = _email.value ?: ""
                val password = _password.value ?: ""

                // Validaciones
                val validationErrors = mutableListOf<String>()

                if (email.isEmpty()) {
                    validationErrors.add("El email es requerido")
                    _emailError.value = "El email es requerido"
                }
                if (password.isEmpty()) {
                    validationErrors.add("La contraseña es requerida")
                    _passwordError.value = "La contraseña es requerida"
                }

                if (validationErrors.isNotEmpty()) {
                    _uiState.value = UiState.Error(
                        message = "Por favor, complete todos los campos",
                        errors = validationErrors
                    )
                    return@launch
                }

                try {
                    // Obtenemos el token o lanzamos una excepción si hay error
                    fcmToken = getFCMToken()
                } catch (e: Exception) {
                    _uiState.value = UiState.Error(
                        message = "No se pudo obtener el token de notificaciones",
                        errors = listOf(e.message ?: "Error desconocido")
                    )
                    return@launch
                }

                // Verificar que tenemos un token válido
                if (fcmToken.isNullOrEmpty()) {
                    _uiState.value = UiState.Error(
                        message = "No se pudo obtener el token de notificaciones",
                        errors = listOf("Token FCM vacío o nulo")
                    )
                    return@launch
                }

                when (val result = loginRepository.login(email, password, fcmToken!!)) {
                    is LoginRepository.Result.Success -> {
                        result.data.access_token.let { token ->
                            tokenManager.saveToken(token)
                            _uiState.value = UiState.Success(token)
                            clearFields()
                        }
                    }
                    is LoginRepository.Result.Error.BadRequest -> {
                        _uiState.value = UiState.Error(
                            message = result.message,
                            errors = result.errors
                        )
                    }
                    is LoginRepository.Result.Error.NetworkError -> {
                        _uiState.value = UiState.Error(
                            message = "Error de conexión: ${result.message}"
                        )

                    }
                    is LoginRepository.Result.Error.ServerError -> {
                        _uiState.value = UiState.Error(
                            message = "Error del servidor: ${result.code} - ${result.message}"
                        )
                    }

                    else -> {}
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
        _email.value = ""
        _password.value = ""
        _emailError.value = null
        _passwordError.value = null
    }

    fun resetState() {
        _uiState.value = UiState.Initial
        clearFields()
    }
}

