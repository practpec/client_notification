import com.example.client_notification.register.data.model.CreateUserRequest
import com.example.client_notification.register.data.model.UserDTO
import com.example.client_notification.register.domain.model.User

object UserMapper {
    fun UserDTO.toDomain(): User {
        return User(
            email = this.email
        )
    }

    fun User.toCreateUserRequest(name: String, password: String, phone: String, fcm_token: String): CreateUserRequest {
        return CreateUserRequest(
            name = name,
            email = this.email,
            password = password,
            phone = phone,
            fcm_token = fcm_token
        )
    }
}