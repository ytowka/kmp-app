package com.example.feature.topics

import com.example.core.analytics.AnalyticsDelegate
import com.example.core.analytics.BaseEvents

object TopicAnalytics : AnalyticsDelegate() {

    private const val SCREEN = "topics"

    fun openScreen() {
        sendEvent(
            location = SCREEN,
            event = BaseEvents.SCREEN_OPEN
        )
    }
}