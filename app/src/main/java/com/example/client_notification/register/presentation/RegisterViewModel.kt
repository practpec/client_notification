package com.example.client_notification.register.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.client_notification.core.FCM.FCMService
import com.example.client_notification.register.data.model.CreateUserRequest
import com.example.client_notification.register.data.repository.RegisterRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

// Cambiamos a AndroidViewModel para tener acceso al contexto de la aplicación
class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val registerRepository = RegisterRepository()
    private val context = application.applicationContext

    sealed class UiState {
        object Initial : UiState()
        object Loading : UiState()
        data class Success(val message: String) : UiState()
        data class Error(val message: String, val errors: List<String>? = null) : UiState()
    }

    private val _uiState = MutableLiveData<UiState>(UiState.Initial)
    val uiState: LiveData<UiState> = _uiState

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _phoneNumber = MutableLiveData("")
    val phoneNumber: LiveData<String> = _phoneNumber

    private val _emailError = MutableLiveData<String?>(null)
    val emailError: LiveData<String?> = _emailError

    private val _nameError = MutableLiveData<String?>(null)
    val nameError: LiveData<String?> = _nameError

    private val _passwordError = MutableLiveData<String?>(null)
    val passwordError: LiveData<String?> = _passwordError

    private val _phoneNumberError = MutableLiveData<String?>(null)
    val phoneNumberError: LiveData<String?> = _phoneNumberError

    // Variable para almacenar el token FCM
    private var fcmToken: String? = null

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

    fun onNameChanged(newName: String) {
        _name.value = newName
        _nameError.value = null
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun onPhoneNumberChanged(newPhoneNumber: String) {
        _phoneNumber.value = newPhoneNumber
    }

    fun onEmailFocusLost() {
        viewModelScope.launch {
            val email = _email.value ?: return@launch
            if (email.isNotEmpty()) {
                validateEmail(email)
            }
        }
    }

    private suspend fun validateEmail(email: String) {
        when (val result = registerRepository.validateEmail(email)) {
            is RegisterRepository.Result.Success -> {
                if (!result.data) {
                    _emailError.value = "Este email ya está en uso"
                } else {
                    _emailError.value = null
                }
            }
            is RegisterRepository.Result.Error.BadRequest -> {
                _emailError.value = result.message
            }
            is RegisterRepository.Result.Error.NetworkError -> {
                _emailError.value = "Error de conexión: ${result.message}"
            }
            is RegisterRepository.Result.Error.ServerError -> {
                _emailError.value = "Error del servidor: ${result.message}"
            }
        }
    }

    fun onRegisterClick() {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading

                val name = _name.value ?: ""
                val email = _email.value ?: ""
                val password = _password.value ?: ""
                val phoneNumber = _phoneNumber.value ?:""

                // Validaciones
                val validationErrors = mutableListOf<String>()

                if (name.isEmpty()) validationErrors.add("El email es requerido")
                if (email.isEmpty()) validationErrors.add("El email es requerido")
                if (password.isEmpty()) validationErrors.add("La contraseña es requerida")
                if (phoneNumber.isEmpty()) validationErrors.add("El numero de telefono es requerida")

                if (validationErrors.isNotEmpty()) {
                    _uiState.value = UiState.Error(
                        message = "Por favor, complete todos los campos",
                        errors = validationErrors
                    )
                    return@launch
                }

                // Obtener el token FCM
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

                // Incluir el token FCM en la solicitud
                val request = CreateUserRequest(
                    name = name,
                    email = email,
                    password = password,
                    phone = phoneNumber,
                    fcm_token = fcmToken!! // Usamos !! porque ya verificamos que no es nulo
                )

                when (val result = registerRepository.createUser(request)) {
                    is RegisterRepository.Result.Success -> {
                        _uiState.value = UiState.Success("Usuario creado exitosamente")
                    }
                    is RegisterRepository.Result.Error.BadRequest -> {
                        _uiState.value = UiState.Error(
                            message = result.message,
                            errors = result.errors
                        )
                    }
                    is RegisterRepository.Result.Error.NetworkError -> {
                        _uiState.value = UiState.Error(
                            message = "Error de conexión: ${result.message}"
                        )
                    }
                    is RegisterRepository.Result.Error.ServerError -> {
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