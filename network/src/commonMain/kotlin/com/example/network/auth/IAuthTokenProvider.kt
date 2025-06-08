package com.example.network.auth

interface IAuthTokenProvider {

    suspend fun refreshAndGetToken() : String?

    suspend fun getToken(): String?
}