package com.example.feature.users.domain.usecase

import com.example.core.domain.UseCase
import com.example.feature.users.domain.dto.UserDto
import com.example.feature.users.domain.repository.UserListRepository
import com.example.feature.users.domain.usecase.GetUserListUseCase.PageResult
import org.koin.core.annotation.Factory

@Factory
class GetUserListUseCase(
    private val userListRepository: UserListRepository
) : UseCase<Int, PageResult>(){

    override suspend fun execute(page: Int): PageResult {
        val data = userListRepository.getUserList(page)
        return PageResult(
            users = data.first,
            page = page,
            hasNextPage = data.second
        )
    }


    class PageResult(
        val users: List<UserDto>,
        val page: Int,
        val hasNextPage: Boolean,
    )
}