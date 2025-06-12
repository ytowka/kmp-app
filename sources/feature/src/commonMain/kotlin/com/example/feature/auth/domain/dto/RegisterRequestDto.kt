package com.example.feature.auth.domain.dto

data class RegisterRequestDto(
    val username: String,
    val avatarUri: String?,
    val email: String,
    val phone: String,
    val fullName: String,
    val password: String,
)