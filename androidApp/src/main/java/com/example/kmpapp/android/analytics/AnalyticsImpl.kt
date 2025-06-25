package com.example.kmpapp.android.analytics

import com.example.core.analytics.IAnalytics
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent

class AnalyticsImpl(
    private val firebaseAnalytics: FirebaseAnalytics
) : IAnalytics {

    override fun sendEvent(location: String, event: String, params: Map<String, Any?>) {
        firebaseAnalytics.logEvent(event) {
            param("location", location)
            params.forEach { (k, v) ->
                param(k, v.toString())
            }
        }
    }
}