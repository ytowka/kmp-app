package com.example.feature.users.ui


import com.example.feature.users.domain.dto.UserDto
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class UserModel @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid,
    val fullName: String,
    val avatarUrl: String?,
    val login: String,
    val email: String,
    val phone: String,
    val role: UserRoleModel,
    val isBlocked: Boolean,
)

@OptIn(ExperimentalUuidApi::class) fun UserDto.toUserModel(): UserModel = UserModel(
    id = id,
    fullName = fullName,
    avatarUrl = avatarUrl,
    login = login,
    email = email,
    phone = phone,
    role = role.toModel(),
    isBlocked = isBlocked,
)
