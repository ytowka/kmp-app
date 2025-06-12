package com.example.feature.review.ui

import com.example.feature.review.domain.dto.ReviewDto

data class ReviewCard(
    val reviewModel: ReviewModel,
    val reviewUserInfo: ReviewUserInfo,
)

fun ReviewDto.toReviewModel(): ReviewCard = ReviewCard(
    reviewModel = ReviewModel(
        id = id,
        contentId = contentId,
        mark = mark,
        writeTime = writeTime,
        contentName = contentName,
        text = text
    ),
    reviewUserInfo = ReviewUserInfo(
        userId = userId,
        userAvatarUrl = userAvatarUrl,
        userName = userName
    )
)
