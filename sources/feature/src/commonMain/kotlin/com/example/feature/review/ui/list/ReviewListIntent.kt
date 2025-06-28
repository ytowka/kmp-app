package com.example.feature.review.ui.list

import com.example.core.paging.PagingState
import com.example.feature.review.ui.ReviewCard

sealed interface ReviewListIntent {

    data object LoadNext : ReviewListIntent
    data class UpdatePagingState(val pagingState: PagingState<ReviewCard>) : ReviewListIntent
}

sealed interface ReviewListSideEffect
