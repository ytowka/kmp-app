package com.example.feature.review.ui

import com.example.feature.review.domain.dto.ReviewDto

data class ReviewModel(
    val id: Long,
    val contentId: Long,
    val contentName: String,
    val mark: Int,
    val writeTime: Long,
    val text: String
)

