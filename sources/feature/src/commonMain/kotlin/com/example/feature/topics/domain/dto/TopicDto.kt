package com.example.feature.topics.domain.dto

data class TopicDto(
    val id: Long,
    val name: String,
    val contentCount: Int,
    val imageUrl: String,
)
