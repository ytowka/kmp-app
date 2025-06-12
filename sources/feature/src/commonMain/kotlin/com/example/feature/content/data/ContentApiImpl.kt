package com.example.feature.content.data

import com.example.api.content.ContentApi
import com.example.api.content.ContentListResponse
import com.example.api.content.ContentResponse
import com.example.network.bodyOrThrow
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Factory

@Factory(binds = [ContentApi::class],)
class ContentApiImpl(
    private val httpClient: HttpClient
) : ContentApi {
    override fun getAllContents(themeId: Long, page: Int): ContentListResponse = runBlocking{
        httpClient.get("/api/content/"){
            parameter("id", themeId)
            parameter("page", page)
        }.bodyOrThrow()
    }

    override fun search(themeId: Long, query: String): List<ContentResponse> = runBlocking{
        httpClient.get("/api/content"){
            parameter("id", themeId)
            parameter("q", query)
        }.bodyOrThrow()
    }

    override fun getById(contentId: Long): ContentResponse = runBlocking{
        httpClient.get("/api/content/$contentId").bodyOrThrow()
    }

    override fun getRecommendedContent(): List<ContentResponse> = runBlocking{
        httpClient.get("/api/content/rec").bodyOrThrow()
    }

    override fun getRecommendedContent(topicId: Long): List<ContentResponse> = runBlocking{
        httpClient.get("/api/content/rec/$topicId").bodyOrThrow()
    }
}