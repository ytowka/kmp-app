package com.example.feature.review.domain.usecase

import com.example.core.domain.SimpleFlowUseCase
import com.example.feature.review.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class SubscribeReviewsUpdate(
    private val reviewRepository: ReviewRepository
): SimpleFlowUseCase<Unit>() {
    override fun execute(): Flow<Unit> {
        return reviewRepository.subscribeReviewUpdate()
    }

}