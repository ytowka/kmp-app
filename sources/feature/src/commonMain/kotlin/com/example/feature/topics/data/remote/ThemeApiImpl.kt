package com.example.feature.topics.data.remote

import com.example.api.theme.ThemeApi
import com.example.api.theme.ThemeResponse
import com.example.network.bodyOrThrow
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Factory

@Factory(binds = [ThemeApi::class])
class ThemeApiImpl(
    private val httpClient: HttpClient
) : ThemeApi{
    override fun getAllThemes(): List<ThemeResponse> = runBlocking {
        httpClient.get("/api/topics").bodyOrThrow()
    }
}