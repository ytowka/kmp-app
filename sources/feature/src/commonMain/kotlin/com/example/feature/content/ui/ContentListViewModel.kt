package com.example.feature.content.ui

import androidx.lifecycle.viewModelScope
import com.example.core.arch.MviViewModel
import com.example.core.paging.PagingResponse
import com.example.feature.content.ContentAnalytics
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
) : MviViewModel<ContentListIntent, ContentListState, ContentListSideEffect>() {

    override suspend fun loadData() {
        ContentAnalytics.openScreen(topicName)
        getNextPage(initialState)
        state.map { it.searchQuery }
            .filter { it.isNotBlank() }
            .distinctUntilChanged()
            .collectLatest {
                val params = SearchContentUseCase.Params(topicId, it)
                val content = searchContentUseCase(params).getOrElse { emptyList() }
                accept(ContentListIntent.UpdateList(content.map { it.toContentModel() }))
            }
    }

    override val initialState: ContentListState = ContentListState(topicName = topicName)

    override fun reduce(state: ContentListState, intent: ContentListIntent): ContentListState {
        return when(intent) {
            is ContentListIntent.Search -> state.copy(searchQuery = intent.query)
            is ContentListIntent.UpdateList -> state.copy(
                searchList = intent.newListData
            )
            is ContentListIntent.UpdatePager -> state.copy(
                pagerState = intent.pagerState
            )
            else -> state
        }
    }

    override fun postProcess(oldState: ContentListState, newState: ContentListState, intent: ContentListIntent) {
        when(intent){
            ContentListIntent.LoadNext -> getNextPage(newState)
            else -> {}
        }
    }

    private fun getNextPage(state: ContentListState){
        viewModelScope.launch {
            state.pagerState.loadNext {
                val params = GetAllContentUseCase.Params(topicId, it)
                val result = getAllContentUseCase(params).getOrThrow()
                PagingResponse(
                    data = result.content.map(ContentDto::toContentModel),
                    page = result.page,
                    hasNextPage = result.hasNextPage
                )
            }.collectLatest { pagingData ->
                accept(ContentListIntent.UpdatePager(pagingData))
            }
        }
    }
}