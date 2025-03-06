package com.example.client_notification.shared.data.mapper

import com.example.client_notification.shared.data.models.OrdersDto
import com.example.client_notification.shared.data.models.OrdersResponse

object OrdersMapper {
    fun mapToDto(response: OrdersResponse): OrdersDto {
        return OrdersDto(
            id = response._id,
            status = response.status,
            date = response.created_at,
            address = response.address,
            notes = response.notes,
            userName = response.user_info.name,
            userEmail = response.user_info.email,
            userPhone = response.user_info.phone
        )
    }
}

