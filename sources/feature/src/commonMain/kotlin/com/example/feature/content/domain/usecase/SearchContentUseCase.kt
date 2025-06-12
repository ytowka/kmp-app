package com.example.feature.content.domain.usecase

import com.example.core.domain.UseCase
import com.example.feature.content.domain.dto.ContentDto
import com.example.feature.content.domain.repository.ContentRepository
import org.koin.core.annotation.Factory

@Factory
class SearchContentUseCase(
    private val contentRepository: ContentRepository
) : UseCase<SearchContentUseCase.Params, List<ContentDto>>(){

    override suspend fun execute(params: Params): List<ContentDto> {
        return contentRepository.searchContent(params.topicId, params.query)
    }

    class Params(
        val topicId: Long,
        val query: String
    )
}