package com.example.feature.users.ui

import com.example.core.analytics.AnalyticsDelegate
import com.example.core.analytics.BaseEvents

object UserAnalytics : AnalyticsDelegate(){

    private const val USER_LIST = "userList"
    private const val USER_INFO = "userInfo"

    fun openUserList() {
        sendEvent(
            location = USER_LIST,
            event = BaseEvents.SCREEN_OPEN
        )
    }

    fun openUserInfo() {
        sendEvent(
            location = USER_INFO,
            event = BaseEvents.SCREEN_OPEN
        )
    }
}