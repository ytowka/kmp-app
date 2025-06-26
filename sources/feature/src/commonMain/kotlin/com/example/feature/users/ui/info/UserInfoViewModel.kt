package com.example.feature.users.ui.info

import androidx.lifecycle.viewModelScope
import com.example.core.arch.MviViewModel
import com.example.core.paging.PagingResponse
import com.example.feature.review.domain.dto.ReviewDto
import com.example.feature.review.domain.usecase.GetReviewsByUserUseCase
import com.example.feature.review.ui.toReviewModel
import com.example.feature.users.domain.usecase.GetMatchScoreUseCase
import com.example.feature.users.domain.usecase.GetMeUseCase
import com.example.feature.users.domain.usecase.GetUserByIdUseCase
import com.example.feature.users.ui.UserAnalytics
import com.example.feature.users.ui.toUserModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Factory
class UserInfoViewModel(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getMatchScoreUseCase: GetMatchScoreUseCase,
    private val reviewsByUserUseCase: GetReviewsByUserUseCase,
    private val getMeUseCase: GetMeUseCase,
    private val userId: String,
) : MviViewModel<UserInfoIntent, UserInfoState, UserInfoSideEffect>(){

    override val initialState: UserInfoState = UserInfoState()

    override suspend fun loadData() {
        UserAnalytics.openUserInfo()
        if(userId == MY_USER_ID){
            val user = getMeUseCase().getOrElse { return }
            accept(UserInfoIntent.SetUserModel(
                userModel = user.toUserModel(),
                isMe = true
            ))
        }else{
            viewModelScope.launch {
                getUserByIdUseCase(userId).onSuccess { result ->
                    accept(
                        UserInfoIntent.SetUserModel(
                            userModel = result.toUserModel(),
                            isMe = false
                        )
                    )
                }
            }
            /*viewModelScope.launch {
                getMatchScoreUseCase(Uuid.parse(userId)).onSuccess { result ->
                    // TODO
                }
            }*/
        }
    }

    override fun reduce(state: UserInfoState, intent: UserInfoIntent): UserInfoState {
        return when(intent){
            is UserInfoIntent.UpdatePagingState -> state.copy(reviewListState = intent.pagingState)
            is UserInfoIntent.SetUserModel -> state.copy(
                userModel = intent.userModel,
                isMe = intent.isMe
            )
            else -> state
        }
    }

    override fun postProcess(oldState: UserInfoState, newState: UserInfoState, intent: UserInfoIntent) {
        when(intent){
            UserInfoIntent.LoadNext -> getNextPage(newState)
            is UserInfoIntent.SetUserModel -> getNextPage(newState)
            else -> {}
        }
    }

    private fun getNextPage(state: UserInfoState){
        val userId = state.userModel?.id ?: return
        viewModelScope.launch {
            state.reviewListState.loadNext {
                val params = GetReviewsByUserUseCase.Params(userId, it)
                val result = reviewsByUserUseCase(params).getOrThrow()
                PagingResponse(
                    data = result.reviews.map(ReviewDto::toReviewModel),
                    page = result.page,
                    hasNextPage = result.hasNextPage
                )
            }.collectLatest { pagingData ->
                accept(UserInfoIntent.UpdatePagingState(pagingData))
            }
        }
    }

    companion object{
        const val MY_USER_ID = ""
    }
}