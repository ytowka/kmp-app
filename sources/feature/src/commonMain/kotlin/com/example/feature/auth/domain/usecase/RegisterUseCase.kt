package com.example.feature.auth.domain.usecase

import com.example.core.domain.UseCase
import com.example.feature.auth.domain.dto.RegisterRequestDto
import com.example.feature.auth.domain.repository.AuthRepository
import org.koin.core.annotation.Factory

@Factory
class RegisterUseCase(
    val authRepository: AuthRepository
): UseCase<RegisterRequestDto, Unit>() {

    override suspend fun execute(params: RegisterRequestDto) {
        authRepository.createAccount(params)
    }


}