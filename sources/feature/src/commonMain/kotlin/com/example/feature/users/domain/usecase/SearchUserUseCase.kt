package com.example.feature.users.domain.usecase

import com.example.core.domain.UseCase
import com.example.feature.users.domain.dto.UserDto
import com.example.feature.users.domain.repository.UserListRepository
import org.koin.core.annotation.Factory

@Factory
class SearchUserUseCase(
    private val userListRepository: UserListRepository
) : UseCase<String, List<UserDto>>() {

    override suspend fun execute(query: String): List<UserDto>{
        return userListRepository.searchUser(query)
    }
}