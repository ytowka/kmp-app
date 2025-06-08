package com.example.api.users

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface UserApi {

    fun getAll(page: Int): UserListResponse
    fun search(query: String): List<UserResponse>
    fun get(id: String): UserResponse
    fun update(user: UserRequest)
    fun getMe() : UserResponse
    fun getUserTasteMatchScore(id: Uuid): UserTasteMatchScoreResponse
}