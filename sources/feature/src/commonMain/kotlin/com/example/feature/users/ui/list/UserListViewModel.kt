package com.example.feature.users.ui.list

import androidx.lifecycle.viewModelScope
import com.example.core.arch.MviViewModel
import com.example.core.paging.PagingResponse
import com.example.feature.users.domain.dto.UserDto
import com.example.feature.users.domain.usecase.GetUserListUseCase
import com.example.feature.users.domain.usecase.SearchUserUseCase
import com.example.feature.users.ui.UserAnalytics
import com.example.feature.users.ui.toUserModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

@Factory
class UserListViewModel(
    private val getUserListUseCase: GetUserListUseCase,
    private val searchUserUseCase: SearchUserUseCase
) : MviViewModel<UserListIntent, UserListState, UserListSideEffect>(){

    override val initialState: UserListState = UserListState()

    override suspend fun loadData() {
        UserAnalytics.openUserList()
        getNextPage(initialState)
        state.map { it.searchQuery }
            .filter { it.isNotBlank() }
            .distinctUntilChanged()
            .collectLatest {
                val users = searchUserUseCase(it).getOrDefault(emptyList())
                accept(UserListIntent.SetSearchList(users.map(UserDto::toUserModel)))
            }
    }

    override fun reduce(state: UserListState, intent: UserListIntent): UserListState {
        return when(intent){
            is UserListIntent.UpdatePagingState -> state.copy(users = intent.pagingState)
            is UserListIntent.Search -> state.copy(searchQuery = intent.query)
            is UserListIntent.SetSearchList -> state.copy(searchUsers = intent.list)
            else -> state

        }
    }

    override fun postProcess(oldState: UserListState, newState: UserListState, intent: UserListIntent) {
        when(intent){
            UserListIntent.LoadNext -> getNextPage(newState)
            else -> {}
        }
    }

    private fun getNextPage(state: UserListState){
        viewModelScope.launch {
            state.users.loadNext {
                val result = getUserListUseCase(it).getOrThrow()
                PagingResponse(
                    data = result.users.map(UserDto::toUserModel),
                    page = result.page,
                    hasNextPage = result.hasNextPage
                )
            }.collectLatest {
                accept(UserListIntent.UpdatePagingState(it))
            }
        }
    }
}