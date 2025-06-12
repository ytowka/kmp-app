package com.example.feature.review.domain.dto

data class ReviewListResponseDto(
    val reviews: List<ReviewDto>,
    val page: Int,
    val hasNextPage: Boolean,
)