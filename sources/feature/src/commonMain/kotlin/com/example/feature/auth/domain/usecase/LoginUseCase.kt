package com.example.feature.auth.domain.usecase

import com.example.core.domain.UseCase
import com.example.feature.auth.domain.dto.LoginRequestDto
import com.example.feature.auth.domain.repository.AuthRepository
import org.koin.core.annotation.Factory

@Factory
class LoginUseCase(
    val authRepository: AuthRepository
): UseCase<LoginRequestDto, Unit>() {

    override suspend fun execute(loginRequestDto: LoginRequestDto) {
        authRepository.login(loginRequestDto)
    }
}