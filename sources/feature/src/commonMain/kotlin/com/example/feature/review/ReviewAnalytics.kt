package com.example.feature.review

import com.example.core.analytics.AnalyticsDelegate
import com.example.core.analytics.BaseEvents

object ReviewAnalytics : AnalyticsDelegate() {

    private const val EDIT_SCREEN = "reviewEdit"
    private const val LIST_SCREEN = "reviewList"

    fun openReviewEdit() {
        sendEvent(
            location = EDIT_SCREEN,
            event = BaseEvents.SCREEN_OPEN
        )
    }

    fun openReviewList() {
        sendEvent(
            location = LIST_SCREEN,
            event = BaseEvents.SCREEN_OPEN
        )
    }
}