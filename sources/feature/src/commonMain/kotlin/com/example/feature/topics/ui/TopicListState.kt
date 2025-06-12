package com.example.feature.topics.ui

import com.example.feature.content.ui.ContentModel


data class TopicListState(
    val topics: List<TopicModel> = listOf(),
    val recommendedContent: List<ContentModel> = listOf()
)