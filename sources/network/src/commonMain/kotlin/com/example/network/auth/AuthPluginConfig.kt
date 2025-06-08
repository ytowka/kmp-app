package com.example.network.auth

interface AuthPluginConfig {
    val authTokenProvider: IAuthTokenProvider
    val logoutUseCase: ILogoutUseCase
}