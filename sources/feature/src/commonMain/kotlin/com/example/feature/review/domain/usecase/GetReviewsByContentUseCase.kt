package com.example.feature.review.domain.usecase

import com.example.core.domain.UseCase
import com.example.feature.review.domain.dto.ReviewListResponseDto
import com.example.feature.review.domain.repository.ReviewRepository
import org.koin.core.annotation.Factory

@Factory
class GetReviewsByContentUseCase(
    private val reviewRepository: ReviewRepository
) : UseCase<GetReviewsByContentUseCase.Params, ReviewListResponseDto>() {

    override suspend fun execute(params: Params): ReviewListResponseDto {
        return reviewRepository.getReviewsByContent(params.contentId, params.page)
    }

    class Params(
        val contentId: Long,
        val page: Int
    )
}