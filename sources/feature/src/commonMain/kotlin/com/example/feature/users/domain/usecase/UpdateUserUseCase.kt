package com.example.feature.users.domain.usecase

import com.example.core.domain.UseCase
import com.example.feature.users.domain.dto.UserDto
import com.example.feature.users.domain.repository.UserListRepository
import org.koin.core.annotation.Factory

@Factory
class UpdateUserUseCase(
    private val userListRepository: UserListRepository
) : UseCase<UserDto, Unit>(){

    override suspend fun execute(params: UserDto) {
        userListRepository.updateUser(params)
    }
}