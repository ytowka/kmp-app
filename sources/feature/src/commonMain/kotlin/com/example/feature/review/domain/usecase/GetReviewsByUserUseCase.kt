package com.example.feature.review.domain.usecase

import com.example.core.domain.UseCase
import com.example.feature.review.domain.dto.ReviewListResponseDto
import com.example.feature.review.domain.repository.ReviewRepository
import org.koin.core.annotation.Factory
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Factory
class GetReviewsByUserUseCase(
    private val reviewRepository: ReviewRepository
) : UseCase<GetReviewsByUserUseCase.Params, ReviewListResponseDto>() {

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun execute(params: Params): ReviewListResponseDto {
       return reviewRepository.getReviewsByUser(params.userId, params.page)
    }

    class Params @OptIn(ExperimentalUuidApi::class) constructor(
        val userId: Uuid,
        val page: Int
    )
}