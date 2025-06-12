package com.example.feature.content.domain.usecase

import com.example.core.domain.UseCase
import com.example.feature.content.domain.dto.ContentListResponseDto
import com.example.feature.content.domain.repository.ContentRepository
import org.koin.core.annotation.Factory

@Factory
class GetAllContentUseCase(
    private val contentRepository: ContentRepository,
) : UseCase<GetAllContentUseCase.Params, ContentListResponseDto>(){

    override suspend fun execute(params: Params): ContentListResponseDto {
        return contentRepository.getAllContent(params.themeId, params.page)
    }

    data class Params(
        val themeId: Long,
        val page: Int
    )
}