package com.example.feature.users.data

import com.example.feature.users.domain.dto.RoleDto
import com.example.feature.users.domain.dto.UserDto
import com.example.feature.users.domain.dto.toRequest
import com.example.feature.users.domain.repository.UserListRepository
import com.example.api.users.RoleApiModel
import com.example.api.users.UserApi
import com.example.api.users.UserResponse
import com.example.network.ApiException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Factory(binds = [UserListRepository::class])
class UserListRepositoryImpl(
    private val userApi: UserApi,
    @Named("baseUrl") private val baseUrl: String
) : UserListRepository {

    private var me: UserDto? = null

    override suspend fun getUserList(page: Int): Pair<List<UserDto>, Boolean> {
        return withContext(Dispatchers.IO) {
            val response = userApi.getAll(page)
            return@withContext response.list.map {
                it.toDto(baseUrl)
            } to response.hasNextPage
        }
    }

    override suspend fun searchUser(query: String): List<UserDto> {
        return withContext(Dispatchers.IO) {
            userApi.search(query).map {
                it.toDto(baseUrl)
            }
        }
    }

    override suspend fun getUserById(id: String): UserDto = withContext(Dispatchers.IO) {
        userApi.get(id).toDto(baseUrl)
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateUser(userDto: UserDto) = withContext(Dispatchers.IO) {
        if (userDto.id == me?.id) {
            me = userDto
        }
        userApi.update(userDto.toRequest())
    }

    override suspend fun getMe(): UserDto {
        val _me = me
        return withContext (Dispatchers.IO) {
            if (_me == null) {
                val account = userApi.getMe().toDto(baseUrl)
                me = account
                account
            } else {
                _me
            }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getMatchScore(userId: Uuid): Float? {
        return try {
            userApi.getUserTasteMatchScore(userId).score
        } catch (e: ApiException){
            return null
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
fun UserResponse.toDto(
    baseUrl: String
): UserDto = UserDto(
    id = Uuid.parse(this.id),
    fullName = this.fullName,
    email = this.email,
    phone = this.phone,
    avatarUrl = baseUrl + this.avatarUrl,
    login = login,
    role = role.toDto(),
    isBlocked = isBlocked,
)

fun RoleApiModel.toDto(): RoleDto = when (this) {
    RoleApiModel.USER -> RoleDto.USER
    RoleApiModel.ADMIN -> RoleDto.ADMIN
}