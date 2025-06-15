package com.example.feature.users.ui.info

import com.example.core.paging.PagingState
import com.example.feature.review.ui.ReviewCard
import com.example.feature.users.ui.UserModel

sealed interface UserInfoIntent {

    data object LoadNext : UserInfoIntent
    data class SetUserModel(val userModel: UserModel, val isMe: Boolean) : UserInfoIntent
    data class UpdatePagingState(val pagingState: PagingState<ReviewCard>) : UserInfoIntent
}

sealed interface UserInfoSideEffect {
}