package com.example.feature.users.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.paging.PagingResponse
import com.example.feature.users.domain.dto.UserDto
import com.example.feature.users.domain.usecase.GetUserListUseCase
import com.example.feature.users.domain.usecase.SearchUserUseCase
import com.example.feature.users.ui.toUserModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory

@Factory
class UserListViewModel(
    private val getUserListUseCase: GetUserListUseCase,
    private val searchUserUseCase: SearchUserUseCase
) : ViewModel(){


    private val _uiState = MutableStateFlow(UserListState())
    val uiState: StateFlow<UserListState> = _uiState

    init {
        viewModelScope.launch {
            getNextPage()
        }
        viewModelScope.launch {
            uiState.map { it.searchQuery }
                .filter { it.isNotBlank() }
                .distinctUntilChanged()
                .collectLatest {
                    val users = searchUserUseCase(it).getOrDefault(emptyList())
                    _uiState.update { it.copy(
                        searchUsers = users.map {
                            it.toUserModel()
                        }
                    ) }
                }
        }
    }

    fun onQueryChange(query: String) {
        _uiState.update {
            it.copy(searchQuery = query)
        }
    }

    fun getNextPage(){
        viewModelScope.launch {
            uiState.value.users.loadNext {
                val result = getUserListUseCase(it).getOrThrow()
                PagingResponse(
                    data = result.users.map(UserDto::toUserModel),
                    page = result.page,
                    hasNextPage = result.hasNextPage
                )
            }.collectLatest {
                _uiState.update { state -> state.copy(users = it) }
            }
        }
    }
}