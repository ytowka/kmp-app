package com.example.core.analytics

object AnalyticHolder : IAnalytics{

    lateinit var analytics: IAnalytics

    override fun sendEvent(location: String, event: String, params: Map<String, Any?>) {
        analytics.sendEvent(location, event, params)
    }
}