package com.example.feature.users.ui.info

import com.example.core.paging.PagingState
import com.example.feature.review.ui.ReviewCard
import com.example.feature.users.ui.UserModel

data class UserInfoState(
    val userModel: UserModel? = null,
    val matchScore: Float? = null,
    val isMe: Boolean = false,
    val reviewListState: PagingState<ReviewCard> = PagingState.initial()

)