package com.example.feature.review.domain.usecase

import com.example.core.domain.UseCase
import com.example.feature.review.domain.repository.ReviewRepository
import org.koin.core.annotation.Factory

@Factory
class DeleteReviewUseCase(
    private val reviewRepository: ReviewRepository
) : UseCase<Long, Unit>(){

    override suspend fun execute(params: Long) {
        reviewRepository.deleteReview(params)
    }
}