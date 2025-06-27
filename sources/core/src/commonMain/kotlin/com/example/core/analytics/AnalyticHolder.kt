package com.example.core.analytics

object AnalyticHolder {

    lateinit var analytics: BaseAnalytics

    fun sendEvent(location: String, event: String, params: Map<String, Any?>) {
        analytics.sendEvent(location, event, params)
    }
}