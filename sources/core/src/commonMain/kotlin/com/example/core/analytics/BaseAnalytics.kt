package com.example.core.analytics

import com.example.core.platform

abstract class BaseAnalytics {

    protected abstract fun sendEventToServer(
        event: String,
        params: Map<String, String?>
    )

    fun sendEvent(
        location: String,
        event: String,
        params: Map<String, Any?>
    ) {
        val mappedParams = params
            .mapValues {
                it.toString()
            } + mapOf(
                "platform" to platform(),
                "location" to location
            )
        sendEventToServer(
            event = event,
            params = mappedParams
        )
    }
}