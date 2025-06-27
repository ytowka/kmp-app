package com.example.kmpapp.android.analytics

import com.example.core.analytics.BaseAnalytics
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent

class AnalyticsImpl(
    private val firebaseAnalytics: FirebaseAnalytics
) : BaseAnalytics() {

    override fun sendEventToServer(event: String, params: Map<String, String?>) {
        firebaseAnalytics.logEvent(event) {
            params.forEach { (k, v) ->
                param(k, v.toString())
            }
        }
    }
}