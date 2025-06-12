package com.example.feature.content.domain.repository

import com.example.feature.content.domain.dto.ContentDto
import com.example.feature.content.domain.dto.ContentListResponseDto
import kotlin.uuid.Uuid

interface ContentRepository {
    suspend fun getAllContent(topicId: Long, page: Int): ContentListResponseDto
    suspend fun getById(contentId: Long): ContentDto
    suspend fun searchContent(topicId: Long, query: String): List<ContentDto>
    suspend fun getRecommendedContent(): List<ContentDto>
    suspend fun getRecommendedContent(topicId: Long): List<ContentDto>
}