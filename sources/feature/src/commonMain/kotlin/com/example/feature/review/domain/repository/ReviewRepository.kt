package com.example.feature.review.domain.repository

import com.example.feature.review.domain.dto.ReviewDto
import com.example.feature.review.domain.dto.ReviewListResponseDto
import com.example.feature.review.domain.dto.ReviewRequestDto
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface ReviewRepository {
    suspend fun getReviewsByContent(contentId: Long, page: Int): ReviewListResponseDto
    @OptIn(ExperimentalUuidApi::class)
    suspend fun getReviewsByUser(userId: Uuid, page: Int): ReviewListResponseDto
    suspend fun getReviewsByUserAndContent(userId: Uuid, contentId: Long): ReviewDto
    suspend fun writeReview(reviewRequest: ReviewRequestDto)
    suspend fun editReview(reviewRequest: ReviewRequestDto)
    suspend fun deleteReview(reviewId: Long)
}