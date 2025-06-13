package com.example.feature.review.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.paging.PagingResponse
import com.example.feature.review.domain.dto.ReviewDto
import com.example.feature.review.domain.usecase.GetReviewsByContentUseCase
import com.example.feature.review.ui.toReviewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory

@Factory
class ReviewListViewModel(
    val reviewsByContentUseCase: GetReviewsByContentUseCase,
    val contentId: Long,
) : ViewModel(){

    val _uiState = MutableStateFlow(ReviewListState())
    val uiState = _uiState.asStateFlow()

    init {
        getNextPage()
    }

    fun getNextPage(){
        viewModelScope.launch {
            uiState.value.listState.loadNext {
                val params = GetReviewsByContentUseCase.Params(contentId, it)
                val result = reviewsByContentUseCase(params).getOrThrow()
                PagingResponse(
                    data = result.reviews.map(ReviewDto::toReviewModel),
                    page = result.page,
                    hasNextPage = result.hasNextPage
                )
            }.collectLatest { pagingData ->
                _uiState.update { it.copy(listState = pagingData) }
            }
        }
    }
}