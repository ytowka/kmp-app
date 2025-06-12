package com.example.feature.review.ui.list

import com.example.core.paging.PagingState
import com.example.feature.review.ui.ReviewCard

data class ReviewListState(
    val contentId: Long = 0,
    val listState: PagingState<ReviewCard> = PagingState.initial(),
)
