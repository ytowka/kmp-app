package com.example.feature.auth.domain.usecase

import com.example.feature.auth.domain.repository.AuthRepository
import com.example.network.auth.ILogoutUseCase
import org.koin.core.annotation.Factory

@Factory(binds = [ILogoutUseCase::class])
class LogoutUseCase(
    val authRepository: AuthRepository
) : ILogoutUseCase {

    override suspend operator fun invoke() {
        authRepository.logout()
    }
}