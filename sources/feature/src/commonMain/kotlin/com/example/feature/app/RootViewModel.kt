package com.example.feature.app

import com.example.core.arch.MviViewModel
import com.example.feature.users.domain.usecase.GetMeUseCase
import com.example.feature.auth.domain.usecase.IsLoggedInUseCase
import kotlinx.coroutines.flow.*
import org.koin.core.annotation.Factory

@Factory
class RootViewModel(
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val getMeUseCase: GetMeUseCase
) : MviViewModel<RootIntent, RootState, RootSideEffect>(){

    override val initialState: RootState = RootState.Loading

    override suspend fun loadData() {
        val currentUser =  flow {
            emit(getMeUseCase().getOrNull())
        }
        val isLoggedIn = isLoggedInUseCase().map { it.getOrDefault(false) }

        isLoggedIn.combine(currentUser){ isLoggedIn, currentUser ->
            accept(RootIntent.Init(isLoggedIn, currentUser))
        }.collect()
    }

    override fun reduce(state: RootState, intent: RootIntent): RootState {
        return when(intent){
            is RootIntent.Init -> RootState.Success(intent.isLoggedIn, intent.currentUser)
        }
    }

    override fun postProcess(oldState: RootState, newState: RootState, intent: RootIntent) {
        when(intent){
            is RootIntent.Init -> if(newState is RootState.Success && !newState.isLoggedIn){
                showSideEffect(RootSideEffect.Auth)
            }
        }
    }
}