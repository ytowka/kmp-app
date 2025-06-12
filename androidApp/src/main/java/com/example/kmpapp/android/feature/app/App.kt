package com.example.kmpapp.android.feature.app

import android.app.Application
import com.example.kmpapp.CommonDi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        CommonDi.initDi()
    }
}