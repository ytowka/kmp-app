package com.example.feature.auth.data.remote

import com.example.api.auth.*
import com.example.network.bodyOrThrow
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory(binds = [AuthApi::class])
class AuthApiImpl(
    @Named("auth") val httpClient: HttpClient
) : AuthApi{

    override fun register(registerRequest: RegisterRequest): TokenPairResponse = runBlocking{
        httpClient.post("api/auth/sign-up") {
            setBody(registerRequest)
        }.bodyOrThrow()
    }

    override fun login(loginRequest: LoginRequest): TokenPairResponse = runBlocking{
        httpClient.post("api/auth/sign-in") {
            setBody(loginRequest)
        }.bodyOrThrow()
    }

    override  fun refreshToken(refreshTokenRequest: RefreshTokenRequest): TokenPairResponse = runBlocking{
        httpClient.post("api/auth/refresh-token") {
            setBody(refreshTokenRequest)
        }.bodyOrThrow()
    }
}