package com.example.feature.users.domain.usecase

import com.example.core.domain.UseCase
import com.example.feature.users.domain.repository.UserListRepository
import org.koin.core.annotation.Factory
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Factory
class GetMatchScoreUseCase(
    private val userListRepository: UserListRepository
) : UseCase<Uuid, Float?>(){

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun execute(userId: Uuid): Float? {
        return userListRepository.getMatchScore(userId)
    }
}