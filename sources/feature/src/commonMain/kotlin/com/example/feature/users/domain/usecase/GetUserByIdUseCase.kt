package com.example.feature.users.domain.usecase

import com.example.core.domain.UseCase
import com.example.feature.users.domain.dto.UserDto
import com.example.feature.users.domain.repository.UserListRepository
import org.koin.core.annotation.Factory

@Factory
class GetUserByIdUseCase(
    private val userListRepository: UserListRepository
) : UseCase<String, UserDto>(){

    override suspend fun execute(params: String): UserDto {
        return userListRepository.getUserById(params)
    }
}