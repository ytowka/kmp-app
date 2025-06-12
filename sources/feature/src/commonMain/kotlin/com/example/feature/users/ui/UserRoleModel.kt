package com.example.feature.users.ui

import com.example.feature.users.domain.dto.RoleDto

enum class UserRoleModel {
    USER,
    ADMIN
}

fun RoleDto.toModel(): UserRoleModel = when (this) {
    RoleDto.ADMIN -> UserRoleModel.ADMIN
    RoleDto.USER -> UserRoleModel.USER
}

fun UserRoleModel.toDto(): RoleDto = when (this) {
    UserRoleModel.ADMIN -> RoleDto.ADMIN
    UserRoleModel.USER -> RoleDto.USER
}