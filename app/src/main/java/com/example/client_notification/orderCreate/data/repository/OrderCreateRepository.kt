//package com.example.client_notification.orderCreate.data.repository
//
//import com.example.client_notification.core.network.retrofit.RetrofitApis
//import com.example.client_notification.core.storage.TokenManager
//import com.example.client_notification.orderCreate.data.mapper.OrderCreateMapper
//import com.example.client_notification.orderCreate.data.models.CreateOrderRequest
//import com.example.client_notification.orderCreate.data.models.OrderCreateDto
//import com.example.client_notification.orderCreate.data.models.OrderResponse
//import com.google.gson.Gson
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import retrofit2.Response
//
//class OrderCreateRepository(private val tokenManager: TokenManager) {
//    private val orderApi = RetrofitApis.getOrderApi(tokenManager)
//    private val gson = Gson()
//
//    sealed class Result<out T> {
//        data class Success<T>(val data: T) : Result<T>()
//        sealed class Error : Result<Nothing>() {
//            data class BadRequest(val message: String, val errors: List<String>? = null) : Error()
//            data class NetworkError(val message: String) : Error()
//            data class ServerError(val code: Int, val message: String) : Error()
//        }
//    }
//
//    data class ErrorResponse(
//        val message: String,
//        val errors: List<String>? = null
//    )
//
//    suspend fun createOrder(title: String, description: String): Result<OrderCreateDto> {
//        return withContext(Dispatchers.IO) {
//            try {
//                val request = CreateOrderRequest(title, description)
//                val response = orderApi.createOrder(request)
//                handleApiResponse<OrderResponse, OrderCreateDto>(response) { orderResponse ->
//                    OrderCreateMapper.mapToDto(orderResponse)
//                }
//            } catch (e: Exception) {
//                Result.Error.NetworkError("Error de red: ${e.message}")
//            }
//        }
//    }
//
//    private fun <T, R> handleApiResponse(
//        response: Response<T>,
//        transform: (T) -> R
//    ): Result<R> {
//        return when (response.code()) {
//            in 200..299 -> {
//                response.body()?.let {
//                    Result.Success(transform(it))
//                } ?: Result.Error.ServerError(500, "Respuesta vacía del servidor")
//            }
//            400 -> {
//                try {
//                    val errorBody = response.errorBody()?.string()
//                    val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
//                    Result.Error.BadRequest(
//                        message = errorResponse.message,
//                        errors = errorResponse.errors
//                    )
//                } catch (e: Exception) {
//                    Result.Error.BadRequest(
//                        message = "Error en la solicitud",
//                        errors = null
//                    )
//                }
//            }
//            in 401..499 -> {
//                Result.Error.ServerError(
//                    code = response.code(),
//                    message = "Error de cliente: ${response.message()}"
//                )
//            }
//            else -> {
//                Result.Error.ServerError(
//                    code = response.code(),
//                    message = "Error de servidor: ${response.message()}"
//                )
//            }
//        }
//    }
//}