package com.example.feature.topics.ui

import com.example.core.arch.MviViewModel
import com.example.feature.content.domain.usecase.GetRecommendedFeedUseCase
import com.example.feature.content.ui.toContentModel
import com.example.feature.topics.TopicAnalytics
import com.example.feature.topics.domain.usecase.GetTopicsUseCase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

@Factory
class TopicViewModel(
    private val getTopicsUseCase: GetTopicsUseCase,
    private val getRecommendedFeedUseCase: GetRecommendedFeedUseCase
) : MviViewModel<TopicListIntent, TopicListState, TopicSideEffect>() {

    override val initialState: TopicListState = TopicListState()

    override suspend fun loadData() {
        TopicAnalytics.openScreen()
        coroutineScope {
            launch {
                val topics = getTopicsUseCase().getOrElse { emptyList() }.map { it.toTopicModel() }
                accept(TopicListIntent.UpdateTopics(topics))
            }
            launch {
                val recommendedContent = getRecommendedFeedUseCase().getOrElse { emptyList() }.map { it.toContentModel() }
                accept(TopicListIntent.UpdateRecommendedContent(recommendedContent))
            }
        }
    }

    override fun reduce(state: TopicListState, intent: TopicListIntent): TopicListState {
        return when(intent){
            is TopicListIntent.UpdateTopics -> state.copy(topics = intent.topics)
            is TopicListIntent.UpdateRecommendedContent -> state.copy(recommendedContent = intent.recommendedContent)
        }
    }

}