package com.example.feature.users.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.paging.PagingResponse
import com.example.feature.review.domain.dto.ReviewDto
import com.example.feature.review.domain.usecase.GetReviewsByUserUseCase
import com.example.feature.review.ui.toReviewModel
import com.example.feature.users.domain.usecase.GetMatchScoreUseCase
import com.example.feature.users.domain.usecase.GetMeUseCase
import com.example.feature.users.domain.usecase.GetUserByIdUseCase
import com.example.feature.users.ui.toUserModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@KoinViewModel
class UserInfoViewModel(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getMatchScoreUseCase: GetMatchScoreUseCase,
    private val reviewsByUserUseCase: GetReviewsByUserUseCase,
    private val getMeUseCase: GetMeUseCase,
    private val userId: String,
) : ViewModel(){

    val _uiState = MutableStateFlow(UserInfoState())
    val uiState = _uiState.asStateFlow()

    init {
        if(userId == MY_USER_ID){
            viewModelScope.launch {
                val user = getMeUseCase().getOrElse { return@launch }
                _uiState.updateAndGet {
                    it.copy(
                        userModel = user.toUserModel(),
                        isMe = true
                    )
                }
                getNextPage()
            }
        }else{
            viewModelScope.launch {
                getUserByIdUseCase(userId).onSuccess { result ->
                    _uiState.update {
                        it.copy(userModel = result.toUserModel())
                    }
                }
                getNextPage()
            }
            viewModelScope.launch {
                getMatchScoreUseCase(Uuid.parse(userId)).onSuccess { result ->
                    _uiState.update {
                        it.copy(matchScore = result)
                    }
                }
            }
        }

    }

    fun getNextPage(){
        viewModelScope.launch {
            uiState.value.reviewListState.loadNext {
                val userId = uiState.first { it.userModel != null }.userModel?.id!!
                val params = GetReviewsByUserUseCase.Params(userId, it)
                val result = reviewsByUserUseCase(params).getOrThrow()
                PagingResponse(
                    data = result.reviews.map(ReviewDto::toReviewModel),
                    page = result.page,
                    hasNextPage = result.hasNextPage
                )
            }.collectLatest { pagingData ->
                _uiState.update { it.copy(reviewListState = pagingData) }
            }
        }
    }

    companion object{
        const val MY_USER_ID = ""
    }
}