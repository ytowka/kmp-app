package com.example.feature.topics.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.content.domain.usecase.GetRecommendedFeedUseCase
import com.example.feature.content.ui.toContentModel
import com.example.feature.topics.domain.usecase.GetTopicsUseCase
import kotlinx.coroutines.flow.*
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class TopicViewModel(
    private val getTopicsUseCase: GetTopicsUseCase,
    private val getRecommendedFeedUseCase: GetRecommendedFeedUseCase
) :ViewModel() {


    val state: StateFlow<TopicListState> = combine(
        flow { emit(getTopicsUseCase()) },
        flow { emit(getRecommendedFeedUseCase()) }
    ){ topics, feed ->
        TopicListState(
            topics = topics.getOrElse { emptyList() }.map { it.toTopicModel() },
            recommendedContent = feed.getOrElse { emptyList() }.map { it.toContentModel() }
        )
    }.stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = TopicListState())


}