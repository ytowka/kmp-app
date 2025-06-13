package com.example.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@Module
actual class DataModule {

    @OptIn(ExperimentalForeignApi::class)
    @Single
    fun datastore() : DataStore<Preferences> {
        return createDataStore(
            producePath = {
                val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null,
                )
                requireNotNull(documentDirectory).path + "/$dataStoreFileName"
            }
        )
    }
}