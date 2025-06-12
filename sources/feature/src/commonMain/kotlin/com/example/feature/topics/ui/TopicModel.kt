package com.example.feature.topics.ui

import com.example.feature.topics.domain.dto.TopicDto

data class TopicModel(
    val id: Long,
    val name: String,
    val contentCount: Int,
    val imageUrl: String,
)

fun TopicDto.toTopicModel() = TopicModel(
    id = id,
    name = name,
    contentCount = contentCount,
    imageUrl = imageUrl
)
