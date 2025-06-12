package com.example.network.auth

import org.koin.core.annotation.Factory

@Factory
class AuthPluginConfig(
    val authTokenProvider: IAuthTokenProvider,
    val logoutUseCase: ILogoutUseCase
)