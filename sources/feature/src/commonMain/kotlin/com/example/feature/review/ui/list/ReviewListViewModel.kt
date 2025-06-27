package com.example.feature.review.ui.list

import androidx.lifecycle.viewModelScope
import com.example.core.arch.MviViewModel
import com.example.core.paging.PagingResponse
import com.example.feature.review.ReviewAnalytics
import com.example.feature.review.domain.dto.ReviewDto
import com.example.feature.review.domain.usecase.GetReviewsByContentUseCase
import com.example.feature.review.domain.usecase.SubscribeReviewsUpdate
import com.example.feature.review.ui.toReviewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

@Factory
class ReviewListViewModel(
    val reviewsByContentUseCase: GetReviewsByContentUseCase,
    val reviewUpdateUseCase: SubscribeReviewsUpdate,
    val contentId: Long,
) : MviViewModel<ReviewListIntent, ReviewListState, ReviewListSideEffect>(){


    override val initialState: ReviewListState = ReviewListState()

    override suspend fun loadData() {
        ReviewAnalytics.openReviewList()
        getNextPage(initialState)
        reviewUpdateUseCase().collect {
            getNextPage(initialState)
        }
    }

    override fun reduce(state: ReviewListState, intent: ReviewListIntent): ReviewListState {
        return when(intent){
            is ReviewListIntent.UpdatePagingState -> state.copy(listState = intent.pagingState)
            else -> state
        }
    }

    override fun postProcess(oldState: ReviewListState, newState: ReviewListState, intent: ReviewListIntent) {
        when(intent){
            ReviewListIntent.LoadNext -> getNextPage(newState)
            else -> {}
        }
    }

    private fun getNextPage(state: ReviewListState){
        viewModelScope.launch {
            state.listState.loadNext {
                val params = GetReviewsByContentUseCase.Params(contentId, it)
                val result = reviewsByContentUseCase(params).getOrThrow()
                PagingResponse(
                    data = result.reviews.map(ReviewDto::toReviewModel),
                    page = result.page,
                    hasNextPage = result.hasNextPage
                )
            }.collectLatest { pagingData ->
                accept(ReviewListIntent.UpdatePagingState(pagingData))
            }
        }
    }
}