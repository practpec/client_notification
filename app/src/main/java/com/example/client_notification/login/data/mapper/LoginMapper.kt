// Asegúrate de que este sea tu LoginMapper
package com.example.client_notification.login.data.mapper

import com.example.client_notification.login.data.model.LoginDTO
import com.example.client_notification.login.data.model.LoginResponse

object LoginMapper {
    fun toLoginDTO(response: LoginResponse): LoginDTO {
        return LoginDTO(
            access_token = response.access_token ?: "",
            token_type = response.token_type ?: ""
        )
    }
}
