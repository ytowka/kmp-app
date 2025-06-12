package com.example.feature.review.domain.usecase

import com.example.core.domain.UseCase
import com.example.feature.review.domain.dto.ReviewRequestDto
import com.example.feature.review.domain.repository.ReviewRepository
import org.koin.core.annotation.Factory

@Factory
class EditReviewUseCase(
    private val reviewRepository: ReviewRepository
) : UseCase<ReviewRequestDto, Unit>(){

    override suspend fun execute(params: ReviewRequestDto) {
        reviewRepository.editReview(params)
    }
}