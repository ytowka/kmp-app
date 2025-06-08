package com.example.api.users

import kotlinx.serialization.Serializable

@Serializable
enum class RoleApiModel {
    ADMIN,
    USER,
}