package com.example.feature.topics.domain.repository

import com.example.feature.topics.domain.dto.TopicDto

interface TopicRepository {
    suspend fun getTopics(): List<TopicDto>
}