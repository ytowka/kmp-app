package com.example.feature.review.domain.dto

data class ReviewUpdateDto(
    val reviewId: Long?,
    val newReview: ReviewDto?
)