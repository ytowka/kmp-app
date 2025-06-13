package com.example.feature.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.users.domain.usecase.GetMeUseCase
import com.example.feature.auth.domain.usecase.IsLoggedInUseCase
import kotlinx.coroutines.flow.*
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory

@Factory
class RootViewModel(
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val getMeUseCase: GetMeUseCase
) : ViewModel(){

    val isLoggedIn = isLoggedInUseCase()
        .map {
            it.getOrDefault(false)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    val currentUser = flow {
        getMeUseCase().onSuccess {
            emit(it)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)
}