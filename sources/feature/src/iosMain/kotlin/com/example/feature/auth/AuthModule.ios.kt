package com.example.feature.auth

import com.example.feature.auth.data.ImageResolver
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
actual class AuthModule actual constructor() {


    @Single
    fun provideImageResolver() = ImageResolver()
}