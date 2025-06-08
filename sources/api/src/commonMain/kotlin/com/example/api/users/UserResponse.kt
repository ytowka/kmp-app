package com.example.api.users

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse (
    val id: String,
    val fullName: String,
    val avatarUrl: String?,
    val login: String,
    val email: String,
    val phone: String,
    val role: RoleApiModel,
    val isBlocked: Boolean,
    val activated: Boolean,
)