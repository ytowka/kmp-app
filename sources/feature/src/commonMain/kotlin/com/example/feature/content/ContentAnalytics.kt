package com.example.feature.content

import com.example.core.analytics.AnalyticsDelegate
import com.example.core.analytics.BaseEvents

object ContentAnalytics : AnalyticsDelegate() {

    private const val SCREEN = "contentList"
    private const val TOPIC_NAME_PARAM = "topicName"

    fun openScreen(
        topicName: String
    ) {
        sendEvent(
            location = SCREEN,
            event = BaseEvents.SCREEN_OPEN,
            params = mapOf(
                TOPIC_NAME_PARAM to topicName
            )
        )
    }
}