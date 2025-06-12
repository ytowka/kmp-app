package com.example.feature.users.ui.list

import com.example.core.paging.PagingState
import com.example.feature.users.ui.UserModel

data class UserListState(
    val users: PagingState<UserModel> = PagingState.initial(),
    val searchUsers: List<UserModel> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
){
    val currentPagingState by lazy {
        if(searchQuery.isBlank()){
            users
        }else{
            PagingState(
                list = searchUsers,
                hasNextPage = false
            )
        }
    }
}