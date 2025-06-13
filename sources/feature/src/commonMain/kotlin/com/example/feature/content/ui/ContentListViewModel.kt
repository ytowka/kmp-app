package com.example.feature.content.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.paging.PagingResponse
import com.example.feature.content.domain.dto.ContentDto
import com.example.feature.content.domain.usecase.GetAllContentUseCase
import com.example.feature.content.domain.usecase.SearchContentUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

@Factory
class ContentListViewModel(
    private val getAllContentUseCase: GetAllContentUseCase,
    private val searchContentUseCase: SearchContentUseCase,
    private val topicId: Long,
    private val topicName: String
) : ViewModel() {


    val _uiState = MutableStateFlow(ContentListState(topicName = topicName))
    val uiState = _uiState.asStateFlow()

    init{
        getNextPage()
        viewModelScope.launch {
            uiState.map { it.searchQuery }
                .filter { it.isNotBlank() }
                .distinctUntilChanged()
                .collectLatest {
                    val params = SearchContentUseCase.Params(topicId, it)
                    val content = searchContentUseCase(params).getOrElse { emptyList() }
                    _uiState.update { it.copy(
                        searchList = content.map { it.toContentModel() }
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
            uiState.value.pagerState.loadNext {
                val params = GetAllContentUseCase.Params(topicId, it)
                val result = getAllContentUseCase(params).getOrThrow()
                PagingResponse(
                    data = result.content.map(ContentDto::toContentModel),
                    page = result.page,
                    hasNextPage = result.hasNextPage
                )
            }.collectLatest { pagingData ->
                _uiState.update { it.copy(pagerState = pagingData) }
            }
        }
    }
}