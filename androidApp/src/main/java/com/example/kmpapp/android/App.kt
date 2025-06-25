package com.example.kmpapp.android

import android.app.Application
import com.example.kmpapp.CommonDi
import com.example.core.analytics.AnalyticHolder
import com.example.kmpapp.android.analytics.AnalyticsImpl
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import org.koin.android.ext.koin.androidContext

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        AnalyticHolder.analytics = AnalyticsImpl(Firebase.analytics)
        CommonDi.initDi {
            androidContext(applicationContext)
        }
    }
}