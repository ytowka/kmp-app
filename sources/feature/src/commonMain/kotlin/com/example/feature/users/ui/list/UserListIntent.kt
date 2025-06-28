package com.example.feature.users.ui.list

import com.example.core.paging.PagingState
import com.example.feature.users.ui.UserModel

sealed interface UserListIntent {

    data object LoadNext : UserListIntent
    data class Search(val query: String) : UserListIntent
    data class UpdatePagingState(val pagingState: PagingState<UserModel>) : UserListIntent
    data class SetSearchList(val list: List<UserModel>) : UserListIntent
}

sealed interface UserListSideEffect