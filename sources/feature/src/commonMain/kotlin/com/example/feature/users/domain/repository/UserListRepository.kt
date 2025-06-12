package com.example.feature.users.domain.repository

import com.example.feature.users.domain.dto.UserDto
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface UserListRepository {

    suspend fun getUserList(page: Int): Pair<List<UserDto>, Boolean>
    suspend fun searchUser(query: String): List<UserDto>
    suspend fun getUserById(id: String): UserDto
    suspend fun updateUser(userDto: UserDto)
    suspend fun getMe(): UserDto
    @OptIn(ExperimentalUuidApi::class)
    suspend fun getMatchScore(userId: Uuid): Float?
}