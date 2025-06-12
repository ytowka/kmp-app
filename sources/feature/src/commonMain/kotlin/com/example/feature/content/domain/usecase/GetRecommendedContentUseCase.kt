package com.example.feature.content.domain.usecase

import com.example.core.domain.UseCase
import com.example.feature.content.domain.dto.ContentDto
import com.example.feature.content.domain.repository.ContentRepository
import org.koin.core.annotation.Factory

@Factory
class GetRecommendedContentUseCase(
    private val contentRepository: ContentRepository
) : UseCase<Long, List<ContentDto>>(){

    override suspend fun execute(params: Long): List<ContentDto> {
        return contentRepository.getRecommendedContent(params)
    }
}