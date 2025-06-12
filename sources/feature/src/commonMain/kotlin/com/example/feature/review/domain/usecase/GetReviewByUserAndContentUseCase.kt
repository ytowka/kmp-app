package com.example.feature.review.domain.usecase

import com.example.core.domain.UseCase
import com.example.feature.review.domain.dto.ReviewDto
import com.example.feature.review.domain.repository.ReviewRepository
import org.koin.core.annotation.Factory
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Factory
class GetReviewByUserAndContentUseCase(
    private val reviewRepository: ReviewRepository,
) : UseCase<GetReviewByUserAndContentUseCase.Params, ReviewDto>(){


    @OptIn(ExperimentalUuidApi::class)
    override suspend fun execute(params: Params): ReviewDto {
        return reviewRepository.getReviewsByUserAndContent(params.userId, params.contentId)
    }


    data class Params @OptIn(ExperimentalUuidApi::class) constructor(
        val userId: Uuid,
        val contentId: Long,
    )
}