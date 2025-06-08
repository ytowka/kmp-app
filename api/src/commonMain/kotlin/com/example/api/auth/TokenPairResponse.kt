package com.example.api.auth

import kotlinx.serialization.Serializable

@Serializable
data class TokenPairResponse(
    val accessToken: String,
    val refreshToken: String,
)