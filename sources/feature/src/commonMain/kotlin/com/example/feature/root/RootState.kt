package com.example.feature.root

import com.example.feature.users.domain.dto.UserDto

sealed interface RootState {

    data object Loading : RootState

    data class Success(
        val isLoggedIn: Boolean,
        val currentUser: UserDto?
    ) : RootState
}