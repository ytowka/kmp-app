package com.example.core.analytics

abstract class AnalyticsDelegate {

    private val analytics = AnalyticHolder.analytics

    protected fun sendEvent(
        location: String,
        event: String,
        params: Map<String, Any?> = emptyMap()
    ) {
        analytics.sendEvent(location, event, params)
    }
}