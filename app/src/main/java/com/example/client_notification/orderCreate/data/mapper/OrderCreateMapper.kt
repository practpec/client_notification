package com.example.client_notification.orderCreate.data.mapper

import com.example.client_notification.orderCreate.data.models.OrderCreateDto
import com.example.client_notification.orderCreate.data.models.OrderResponse

object OrderCreateMapper {

    fun mapToDto(response: OrderResponse): OrderCreateDto {
        return OrderCreateDto(
            notes = response.notes,
            address = response.address
        )
    }
}