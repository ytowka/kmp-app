package com.example.feature.topics.domain.usecase

import com.example.core.domain.SimpleUseCase
import com.example.feature.topics.domain.dto.TopicDto
import com.example.feature.topics.domain.repository.TopicRepository
import org.koin.core.annotation.Factory

@Factory
class GetTopicsUseCase(
    private val topicRepository: TopicRepository
) : SimpleUseCase<List<TopicDto>>(){

    override suspend fun execute(): List<TopicDto> {
        return topicRepository.getTopics()
    }
}