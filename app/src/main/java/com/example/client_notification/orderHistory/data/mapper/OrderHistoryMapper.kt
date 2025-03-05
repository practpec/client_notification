package com.example.client_notification.orderHistory.data.mapper

import com.example.client_notification.orderHistory.data.models.OrderHistoryDto
import com.example.client_notification.orderHistory.data.models.OrderHistoryResponse

object OrderHistoryMapper {
    fun mapToDto(response: OrderHistoryResponse): OrderHistoryDto {
        return OrderHistoryDto(
            id = response.id,
            title = response.title,
            description = response.description,
            creatorId = response.creator_id
        )
    }
}