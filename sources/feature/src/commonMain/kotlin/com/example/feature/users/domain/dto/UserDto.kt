package com.example.feature.users.domain.dto

import com.example.api.users.RoleApiModel
import com.example.api.users.UserRequest
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class UserDto @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid,
    val fullName: String,
    val avatarUrl: String?,
    val login: String,
    val email: String,
    val phone: String,
    val role: RoleDto,
    val isBlocked: Boolean
)

@OptIn(ExperimentalUuidApi::class)
fun UserDto.toRequest(): UserRequest = UserRequest(
    fullName = fullName,
    login = login,
    email = email,
    phone = phone,
    id = id.toString(),
    role = role.toApiModel(),
    isBlocked = isBlocked,
    password = null,
)

fun RoleDto.toApiModel(): RoleApiModel = when(this){
    RoleDto.USER -> RoleApiModel.USER
    RoleDto.ADMIN -> RoleApiModel.ADMIN
}

