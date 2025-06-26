package com.example.feature.auth

import com.example.core.analytics.AnalyticsDelegate
import com.example.core.analytics.BaseEvents

object AuthAnalytics : AnalyticsDelegate() {

    private const val LOGIN_SCREEN = "login"
    private const val REGISTER_SCREEN = "register"

    fun openLoginScreen() {
        sendEvent(
            location = LOGIN_SCREEN,
            event = BaseEvents.SCREEN_OPEN
        )
    }

    fun openRegisterScreen() {
        sendEvent(
            location = REGISTER_SCREEN,
            event = BaseEvents.SCREEN_OPEN
        )
    }
}