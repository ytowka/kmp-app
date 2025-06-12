package com.example.feature.users.domain.usecase

import com.example.core.domain.SimpleUseCase
import com.example.feature.users.domain.dto.UserDto
import com.example.feature.users.domain.repository.UserListRepository
import org.koin.core.annotation.Factory

@Factory
class GetMeUseCase(
    private val userListRepository: UserListRepository
) : SimpleUseCase<UserDto>(){

    override suspend fun execute(): UserDto {
        return userListRepository.getMe()
    }
}