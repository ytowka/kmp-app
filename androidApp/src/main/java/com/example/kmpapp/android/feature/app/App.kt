package com.example.kmpapp.android.feature.app

import android.app.Application
import com.example.kmpapp.CommonDi
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.ksp.generated.module

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        Napier.base(DebugAntilog())
        CommonDi.initDi {
            androidLogger(Level.DEBUG)
            androidContext(applicationContext)
        }
    }
}