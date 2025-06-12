package com.example.feature.auth.domain.dto

data class TokenPairDto(
    val accessToken: String,
    val refreshToken: String
)