package com.example.client_notification.login.data.repository

import android.util.Log
import com.example.client_notification.core.network.retrofit.RetrofitApis
import com.example.client_notification.login.data.mapper.LoginMapper
import com.example.client_notification.login.data.model.LoginDTO
import com.example.client_notification.login.data.model.LoginRequest
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginRepository {
    private val loginApi = RetrofitApis.getLoginApi()
    private val gson = Gson()

    // Result wrapper for success and error cases
    sealed class Result<out T> {
        data class Success<T>(val data: T) : Result<T>()
        sealed class Error : Result<Nothing>() {
            data class BadRequest(val message: String, val errors: List<String>? = null) : Error()
            data class NetworkError(val message: String) : Error()
            data class ServerError(val code: Int, val message: String) : Error()
        }
    }

    data class ErrorResponse(
        val message: String,
        val errors: List<String>? = null
    )

    suspend fun login(email: String, password: String, fcmToken: String): Result<LoginDTO> {
        return withContext(Dispatchers.IO) {
            try {
                // Realiza la solicitud de login
                val response = loginApi.login(LoginRequest(email,password, fcmToken))
                handleApiResponse(response) { loginResponse ->
                    Log.d("testeando", loginResponse.toString())
                    LoginMapper.toLoginDTO(loginResponse)
                }
            } catch (e: Exception) {
                Result.Error.NetworkError("Error de red: ${e.message}")
            }
        }
    }

    // This function handles the response from the API and maps it
    private fun <T, R> handleApiResponse(
        response: Response<T>,
        transform: (T) -> R
    ): Result<R> {
        return when (response.code()) {
            in 200..299 -> {
                response.body()?.let {
                    Result.Success(transform(it)) // Aquí aseguramos que el tipo devuelto sea LoginDTO
                } ?: Result.Error.ServerError(500, "Respuesta vacía del servidor")
            }
            400 -> {
                try {
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                    Result.Error.BadRequest(
                        message = errorResponse.message,
                        errors = errorResponse.errors
                    )
                } catch (e: Exception) {
                    Result.Error.BadRequest(
                        message = "Error en la solicitud",
                        errors = null
                    )
                }
            }
            in 401..499 -> {
                Result.Error.ServerError(
                    code = response.code(),
                    message = "Error de cliente: ${response.message()}"
                )
            }
            else -> {
                Result.Error.ServerError(
                    code = response.code(),
                    message = "Error de servidor: ${response.message()}"
                )
            }
        }
    }
}
