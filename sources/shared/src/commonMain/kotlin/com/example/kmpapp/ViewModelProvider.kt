package com.example.kmpapp

import com.example.feature.root.RootViewModel
import org.koin.core.component.KoinComponent

object ViewModelProvider : KoinComponent {

    fun getRootViewModel(): RootViewModel {
        return getKoin().get()
    }
}