package com.example.feature.app

import com.example.feature.users.domain.dto.UserDto

sealed interface RootIntent {

    data class Init(
        val isLoggedIn: Boolean,
        val currentUser: UserDto?
    ) : RootIntent
}

sealed interface RootSideEffect {
    data object Auth : RootSideEffect
}