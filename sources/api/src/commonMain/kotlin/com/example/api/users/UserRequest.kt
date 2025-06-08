package com.example.api.users

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val id: String,
    val fullName: String,
    val login: String,
    val email: String,
    val phone: String,
    val password: String?,
    val role: RoleApiModel,
    val isBlocked: Boolean
)