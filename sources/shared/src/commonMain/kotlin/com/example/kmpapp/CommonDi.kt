package com.example.kmpapp


import com.example.data.DataModule
import com.example.feature.FeatureModule
import com.example.feature.root.RootViewModel
import com.example.network.NetworkModule
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.ksp.generated.*

object CommonDi {

    fun initDi(
        appDeclaration: KoinAppDeclaration = {},
    ): KoinApplication {
        return startKoin {
            appDeclaration()
            modules(
                NetworkModule().module,
                DataModule().module,
                FeatureModule().module
            )
        }
    }
}