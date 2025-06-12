package com.example.feature.users.data.remote

import com.example.api.users.*
import com.example.network.bodyOrThrow
import io.ktor.client.*

import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Factory
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Factory(binds = [UserApi::class])
class UserApiImpl(
    private val httpClient: HttpClient,
) : UserApi{

    override fun getAll(page: Int): UserListResponse = runBlocking{
        httpClient.get("/api/users"){
            parameter("page", page)
        }.bodyOrThrow()
    }

    override fun search(query: String): List<UserResponse> = runBlocking{
        httpClient.get("/api/users"){
            parameter("q", query)
        }.bodyOrThrow()
    }

    override fun get(id: String): UserResponse = runBlocking{
        httpClient.get("/api/users/$id").bodyOrThrow()
    }

    override fun update(user: UserRequest) = runBlocking{
        httpClient.put("/api/users") {
            setBody(user)
        }
        Unit
    }

    override fun getMe(): UserResponse = runBlocking{
        httpClient.get("/api/users/me").bodyOrThrow()
    }

    @ExperimentalUuidApi
    override fun getUserTasteMatchScore(id: Uuid): UserTasteMatchScoreResponse = runBlocking{
        httpClient.get("/match-score/$id}").bodyOrThrow()
    }
}