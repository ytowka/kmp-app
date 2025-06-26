package com.example.kmpapp.android

import android.app.Application
import com.example.kmpapp.CommonDi
import com.example.core.analytics.AnalyticHolder
import com.example.kmpapp.android.analytics.AnalyticsImpl
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.initialize
import org.koin.android.ext.koin.androidContext

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(this)
        AnalyticHolder.analytics = AnalyticsImpl(FirebaseAnalytics.getInstance(this))
        CommonDi.initDi {
            androidContext(applicationContext)
        }
    }
}