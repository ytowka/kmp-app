package com.example.kmpapp

import com.example.data.DataModule
import com.example.network.NetworkModule
import org.koin.core.context.startKoin
import org.koin.ksp.generated.*

object CommonDi {

    fun initDi() {


        startKoin {
            modules(
                NetworkModule().module,
                DataModule().module,
            )
        }
    }
}