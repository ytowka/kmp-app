package com.example.feature.auth.domain.repository

class AccessTokenDto(
    val token: String,
    val expiresIn: Long,
) {
}