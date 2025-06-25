package com.example.core.analytics

interface IAnalytics {

    fun sendEvent(
        location: String,
        event: String,
        params: Map<String, Any?>
    )
}