package com.example.feature.content.domain.usecase

import com.example.core.domain.UseCase
import com.example.feature.content.domain.dto.ContentDto
import com.example.feature.content.domain.repository.ContentRepository
import org.koin.core.annotation.Factory

@Factory
class GetContentByIdUseCase(
    private val contentRepository: ContentRepository
) : UseCase<Long, ContentDto>(){
    override suspend fun execute(params: Long): ContentDto {
        return contentRepository.getById(params)
    }
}