package com.example.feature.topics.ui

import com.example.feature.content.ui.ContentModel

sealed interface TopicListIntent {

    data class UpdateTopics(
        val topics: List<TopicModel>
    ) : TopicListIntent

    data class UpdateRecommendedContent(
        val recommendedContent: List<ContentModel>
    ) : TopicListIntent
}

sealed interface TopicSideEffect
