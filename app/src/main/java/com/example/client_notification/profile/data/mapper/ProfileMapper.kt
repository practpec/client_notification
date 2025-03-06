package com.example.client_notification.profile.data.mapper

import com.example.client_notification.profile.data.models.ProfileData
import com.example.client_notification.profile.data.models.ProfileDto

object ProfileMapper {
    fun mapToDto(userData: ProfileData): ProfileDto {
        return ProfileDto(
            id = userData._id,
            name = userData.name,
            email = userData.email,
            phone = userData.phone
        )
    }
}

