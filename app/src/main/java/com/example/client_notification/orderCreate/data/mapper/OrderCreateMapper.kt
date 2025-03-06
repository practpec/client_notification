package com.example.client_notification.orderCreate.data.mapper

import com.example.client_notification.orderCreate.data.models.OrderCreateDto
import com.example.client_notification.orderCreate.data.models.OrderResponse

object OrderCreateMapper {

    fun mapToDto(response: OrderResponse): OrderCreateDto {
        return OrderCreateDto(
            id = response.order._id,
            notes = response.order.notes,
            address = response.order.address,
            status = response.order.status,
            createdAt = response.order.created_at
        )
    }
}

