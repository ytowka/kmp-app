package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.annotation.Single

@Single
actual class DataStoreFactory(
    private val context: Context
) {

    actual fun getDatastore(): DataStore<Preferences> {
        return createDataStore(
            producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
        )
    }
}