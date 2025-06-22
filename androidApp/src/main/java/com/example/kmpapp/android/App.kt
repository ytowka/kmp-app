package com.example.kmpapp.android

import android.app.Application
import com.example.kmpapp.CommonDi
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        CommonDi.initDi {
            androidLogger(Level.DEBUG)
            androidContext(applicationContext)
        }
    }
}