package com.example.feature.auth.domain.usecase

import com.example.core.domain.SimpleFlowUseCase
import com.example.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class IsLoggedInUseCase(
    val authRepository: AuthRepository
): SimpleFlowUseCase<Boolean>() {

    override fun execute(): Flow<Boolean> {
        return authRepository.getAccessToken().map {
            it != null
        }
    }
}