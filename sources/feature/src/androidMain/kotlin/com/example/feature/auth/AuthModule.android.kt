package com.example.feature.auth

import android.content.ContentResolver
import android.content.Context
import com.example.feature.auth.data.ImageResolver
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan(value = ["com.example.feature.auth"])
actual class AuthModule actual constructor() {

    @Single
    fun provideContentResolver(
        context: Context
    ) = context.contentResolver

    @Single
    fun provideImageResolver(
        contentResolver: ContentResolver
    ) = ImageResolver(contentResolver)

}