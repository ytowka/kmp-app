package com.example.feature.content.domain.usecase

import com.example.core.domain.SimpleUseCase
import com.example.feature.content.domain.dto.ContentDto
import com.example.feature.content.domain.repository.ContentRepository
import org.koin.core.annotation.Factory

@Factory
class GetRecommendedFeedUseCase(
    private val contentRepository: ContentRepository
) : SimpleUseCase<List<ContentDto>>(){

    override suspend fun execute(): List<ContentDto> {
        return contentRepository.getRecommendedContent()
    }
}