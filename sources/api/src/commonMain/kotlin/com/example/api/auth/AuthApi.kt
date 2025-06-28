package com.example.api.auth

import com.example.api.auth.RefreshTokenRequest
import com.example.api.auth.RegisterRequest
import com.example.api.auth.TokenPairResponse

interface AuthApi {

    fun register(registerRequest: RegisterRequest): TokenPairResponse
    fun login(loginRequest: LoginRequest): TokenPairResponse
    fun refreshToken(refreshTokenRequest: RefreshTokenRequest): TokenPairResponse
}